package com.opinionmining.restservice.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.opinionmining.restservice.entity.Topics;
import com.opinionmining.restservice.exception.TopicsNotFoundException;
import com.opinionmining.restservice.repository.TopicsRepository;

@Service
public class TopicsService {
	
	private final TopicsRepository topicsRepository;
	
	TopicsService(TopicsRepository t){
		this.topicsRepository = t;
	}
	
	public List<Topics> findByUserId(String user_id){
		Optional<List<Topics>> optional_topics = this.topicsRepository.findByUserId(new ObjectId(user_id));
		if(optional_topics.isPresent())
			return optional_topics.get();
		throw new TopicsNotFoundException();
	}
}
