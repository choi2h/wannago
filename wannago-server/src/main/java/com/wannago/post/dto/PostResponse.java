package com.wannago.post.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;


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
    private List<ScheduleInfo> schedules;
    private List<String> tags;
    private PostStatusInfo statusInfo;
}
