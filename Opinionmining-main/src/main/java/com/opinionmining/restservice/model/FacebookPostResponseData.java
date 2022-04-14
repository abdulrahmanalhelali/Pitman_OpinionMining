package com.opinionmining.restservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
public class FacebookPostResponseData {

    @Getter
    @Setter
    private Date created_time;
    public Date getCreated_time() {
		return this.created_time;
	}

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
