package com.opinionmining.restservice.model;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class FacebookPostResponse {

    @Getter
    @Setter
    private List<FacebookPostResponseData> data;

    @Getter
    @Setter
    private FacebookResponsePaging paging;

	public List<FacebookPostResponseData> getData() {
		
		return this.data;
	}

}
