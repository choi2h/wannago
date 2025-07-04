package com.wannago.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder @Getter
@AllArgsConstructor
public class PostSummaryInfo {
    private Long id;
    private String title;
    private String author;
    private String contents;
    private boolean isPublic;
    private List<String> tags;
    private int likeCount;
    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDateTime createdAt;
}
