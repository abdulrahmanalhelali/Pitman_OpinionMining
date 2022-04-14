package com.opinionmining.restservice.repository;

import com.opinionmining.restservice.entity.FeedData;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeedDataRepository extends MongoRepository<FeedData, String> {
}
