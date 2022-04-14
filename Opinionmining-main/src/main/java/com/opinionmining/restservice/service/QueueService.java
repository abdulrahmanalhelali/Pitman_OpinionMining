package com.opinionmining.restservice.service;

import com.opinionmining.restservice.datamining.FacebookData;
import com.opinionmining.restservice.datamining.TwitterData;
import com.opinionmining.restservice.entity.FeedData;
import com.opinionmining.restservice.entity.Queue;
import com.opinionmining.restservice.entity.User;
import com.opinionmining.restservice.repository.QueueRepository;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QueueService {

    private final QueueRepository queueRepository;
    private final UserService userService;
    private final FeedDataService feedDataService;

    public QueueService(QueueRepository queueRepository, @Lazy UserService userService,
                        FeedDataService feedDataService) {
        this.queueRepository = queueRepository;
        this.userService = userService;
        this.feedDataService = feedDataService;
    }

    @Scheduled(initialDelay = 1000 * 15, fixedDelay = 1000 * 60 * 5)
    public void queueWorker(){
        System.out.println("QUEUE PROCESS WORKING");
        List<Queue> queues = queueRepository.findAll();
        for (Queue queue: queues){
            while (queue.getQueue().size() > 0){
                ObjectId id = queue.getQueue().get(0);
                User user = userService.findUserById(id);
                List<FeedData> feeds = new ArrayList<>();
                if (queue.getPlatform().equals("Facebook")){
                    feeds.addAll(FacebookData.facebookDataRetriever(user.getFbToken(), feedDataService));
                }else if (queue.getPlatform().equals("Twitter")){
                    feeds.addAll(TwitterData.twitterDataRetriever(user.getTwToken(), feedDataService));
                }
                feeds.forEach(feedData -> feedData.setUser_id(id));
                feedDataService.saveAll(feeds);
                queue.getQueue().remove(id);
            }
        }
        queueRepository.saveAll(queues);
        System.out.println("QUEUE PROCESS STOPPED");
    }

    public Queue findByPlatform(String platform) {
        return queueRepository.findByPlatform(platform);
    }

    public void addToQueue(String username, String platform) {
        User user = userService.findUserByUsername(username);
        Queue queue = findByPlatform(platform);
        queue.getQueue().add(user.getId());
        queueRepository.save(queue);
    }
}
