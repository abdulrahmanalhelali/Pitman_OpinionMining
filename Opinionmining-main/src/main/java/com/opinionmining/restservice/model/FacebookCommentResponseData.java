package com.opinionmining.restservice.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class FacebookCommentResponseData {


    @Getter
    @Setter
    private Date created_time;
    public Date getCreated_time() {
		return this.created_time;
	}

    @Getter
    @Setter
    private FacebookCommentFrom from;

    @Getter
    @Setter
    private String message;
    public String getMessage() {
		return this.message;
	}

    @Getter
    @Setter
    private String id;
    public String getId() {
		return this.id;
	}
	
}
