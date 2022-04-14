package com.opinionmining.restservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class FeedData {

    @Getter
    @Setter
    private ObjectId user_id;
    public void setUser_id(ObjectId id2) {
		this.user_id = id2;
	}
    

    @Getter
    @Setter
    private String id;
    public void setId(String valueOf) {
		this.id = valueOf;
	}

    @Getter
    @Setter
    private Date created_time;
    public void setCreated_time(Date createdAt) {
		this.created_time = createdAt;
	}

    @Getter
    @Setter
    private String message;
    public void setMessage(String text) {
		this.message = text;
	}

    @Getter
    @Setter
    private List<Reply> replies;
	public void setReplies(List<Reply> replies2) {
		this.replies = replies2;
		
	}
	
	
	

	

}
