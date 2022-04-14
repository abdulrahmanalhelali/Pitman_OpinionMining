package com.opinionmining.restservice.model;

import lombok.Getter;
import lombok.Setter;

public class PagingCursor {

    @Getter
    @Setter
    private String before;

    @Getter
    @Setter
    private String after;
}
