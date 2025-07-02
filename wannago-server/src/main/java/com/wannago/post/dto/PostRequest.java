package com.wannago.post.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostRequest {
    private String title;
    private String contents;
    private String author;
    private boolean isPublic;
    private int likeCount;
    private boolean liked;
    private Long id;
    private LocalDateTime createdAt;
    private boolean isAccepted;
}
