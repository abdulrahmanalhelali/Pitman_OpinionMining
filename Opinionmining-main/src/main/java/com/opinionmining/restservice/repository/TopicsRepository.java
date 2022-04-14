package com.opinionmining.restservice.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.opinionmining.restservice.entity.Topics;

public interface TopicsRepository extends MongoRepository<Topics, ObjectId>{
	Optional<List<Topics>> findByUserId(ObjectId id);
}
