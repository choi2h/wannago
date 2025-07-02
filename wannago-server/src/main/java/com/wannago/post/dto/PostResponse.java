package com.wannago.post.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private Long id;
    private String title;
    private String author;
    private String contents;
    private boolean isPublic;
    private int likeCount;
    private boolean liked;
    private LocalDateTime createdAt;
     private boolean isAccepted;
}
