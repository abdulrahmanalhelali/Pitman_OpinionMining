package com.opinionmining.restservice.repository;

import com.opinionmining.restservice.entity.Queue;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QueueRepository extends MongoRepository<Queue, ObjectId> {
    Queue findByPlatform(String platform);
}
