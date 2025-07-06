package com.wannago.post.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyPostResponse {
    private Long postId;
    private String title;
    private boolean isPublic;
    private List<String> hashtags;
    private int likeCount;
    private boolean liked;
    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDateTime createdDate;
}