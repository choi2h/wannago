package com.wannago.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostRequest {
    private String title;
    private String contents;
    private String author;
    private boolean isPublic;
}
