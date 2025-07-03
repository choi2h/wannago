package com.wannago.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
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
