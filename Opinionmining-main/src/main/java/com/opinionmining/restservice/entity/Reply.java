package com.opinionmining.restservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
public class Reply {

    public Reply(Date createdAt, String text, String valueOf) {
    	this.created_time = createdAt;
    	this.message = text;
    	this.id = valueOf;
	}

	@Getter
    @Setter
    private Date created_time;

    @Getter
    @Setter
    private String message;

    @Getter
    @Setter
    private String id;

}
