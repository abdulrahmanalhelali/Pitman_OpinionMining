package com.opinionmining.restservice.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class FacebookCommentResponse {

    @Getter
    @Setter
    private List<FacebookCommentResponseData> data;

    @Getter
    @Setter
    private FacebookCommentResponsePaging paging;

	public List<FacebookCommentResponseData> getData() {
		return this.data;
	}
}
