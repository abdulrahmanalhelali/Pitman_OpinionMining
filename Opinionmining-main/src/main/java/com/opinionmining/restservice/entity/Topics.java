package com.opinionmining.restservice.entity;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import com.opinionmining.restservice.model.SentimentRatio;

import lombok.Getter;
import lombok.Setter;

@Document
public class Topics {
	
	@Id
	@Getter
    @Setter
    private ObjectId userId;
    
    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private Date createTime;

    @Getter
    @Setter
    private String message;

    @Getter
    @Setter
    private List<Reply> replies;
	
    @Getter
    @Setter
    private List<String> probability;
    
    @Getter
    @Setter
    private List<String> topic;
    
    @Getter
    @Setter
    private String messageSentiment;
    
    @Getter
    @Setter
    private List<SentimentRatio> repliesSentiment;
    
}
