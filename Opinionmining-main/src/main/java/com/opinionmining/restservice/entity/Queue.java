package com.opinionmining.restservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class Queue {

    @Getter
    @Setter
    private ObjectId id;

    @Getter
    @Setter
    private String platform;

    @Getter
    @Setter
    private List<ObjectId> queue;

    public Queue(String platform) {
        this.platform = platform;
        this.queue = new ArrayList<>();
    }

	public List<ObjectId> getQueue() {
		return this.queue;
	}

	public String getPlatform() {
		
		return this.platform;
	}
}
