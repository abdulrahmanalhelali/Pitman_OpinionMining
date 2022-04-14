package com.opinionmining.restservice.service;

import com.opinionmining.restservice.entity.FeedData;
import com.opinionmining.restservice.repository.FeedDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedDataService {

    private final FeedDataRepository feedDataRepository;

    FeedDataService(FeedDataRepository feedDataRepository){
        this.feedDataRepository = feedDataRepository;
    }

    public void saveAll(List<FeedData> feeds){
        feedDataRepository.saveAll(feeds);
    }

    public Optional<FeedData> findById(String id){
        return feedDataRepository.findById(id);
    }

}
