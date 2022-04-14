package com.opinionmining.restservice.model;

import lombok.Getter;
import lombok.Setter;

public class SentimentRatio {
	@Getter
    @Setter
    private String pos;
	
	@Getter
    @Setter
    private String neg;
}
