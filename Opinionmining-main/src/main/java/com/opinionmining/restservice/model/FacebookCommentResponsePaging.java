package com.opinionmining.restservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class FacebookCommentResponsePaging {

    @Getter
    @Setter
    private PagingCursor cursors;

}
