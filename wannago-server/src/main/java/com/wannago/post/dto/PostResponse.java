package com.wannago.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class PostResponse {
    private Long id;
    private String title;
    private String author;
    private String contents;
    private boolean isPublic;
    private int likeCount;
    private boolean liked;
}
