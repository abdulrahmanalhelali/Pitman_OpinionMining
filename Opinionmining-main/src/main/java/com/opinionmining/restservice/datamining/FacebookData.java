package com.opinionmining.restservice.datamining;

import com.opinionmining.restservice.entity.FeedData;
import com.opinionmining.restservice.entity.Reply;
import com.opinionmining.restservice.model.FacebookCommentResponse;
import com.opinionmining.restservice.model.FacebookCommentResponseData;
import com.opinionmining.restservice.model.FacebookPostResponse;
import com.opinionmining.restservice.model.FacebookPostResponseData;
import com.opinionmining.restservice.service.FeedDataService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;

@Component
public class FacebookData{


    public static List<FeedData> facebookDataRetriever(String fbToken, FeedDataService feedDataService){

        List<FeedData> feeds = new ArrayList<>();
        try {

            WebClient client = WebClient.builder()
                    .baseUrl("https://graph.facebook.com/v11.0")
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .defaultUriVariables(Collections.singletonMap("url", "https://graph.facebook.com/v11.0"))
                    .build();

            Mono<FacebookPostResponse> monoBody = client.get().uri("/me/feed?access_token="+fbToken).retrieve()
                    .bodyToMono(FacebookPostResponse.class);
            FacebookPostResponse resp = monoBody.block();

            if (null != resp){
                for (FacebookPostResponseData data: resp.getData()){
                    if (data.getMessage() != null){
                        String id = data.getId();
                        Optional<FeedData> feedData = feedDataService.findById(id);
                        if (!feedData.isPresent()){

                            String message = data.getMessage();
                            Date date = data.getCreated_time();

                            Mono<FacebookCommentResponse> mono = client.get().uri("/"+id+"/comments?access_token="
                                    +fbToken)
                                    .retrieve().bodyToMono(FacebookCommentResponse.class);
                            FacebookCommentResponse commentResponse = mono.block();
                            List<Reply> replies = new ArrayList<>();
                            if (null != commentResponse){
                                for (FacebookCommentResponseData responseData: commentResponse.getData()){
                                    if (responseData.getMessage() != null){
                                        replies.add(new Reply(responseData.getCreated_time(), responseData.getMessage(),
                                                responseData.getId()));
                                    }
                                }
                            }
                            FeedData feed = new FeedData();
                            feed.setId(id);
                            feed.setCreated_time(date);
                            feed.setMessage(message);
                            feed.setReplies(replies);
                            feeds.add(feed);

                        }

                    }
                }
            }



        }catch (Exception e){
            e.printStackTrace();
        }
        return feeds;

    }

}