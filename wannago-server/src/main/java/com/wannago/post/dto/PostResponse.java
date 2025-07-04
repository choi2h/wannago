package com.wannago.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
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
    private List<DailyScheduleInfo> schedules;
    private List<String> tags;
    private PostStatusInfo statusInfo;
    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDateTime createdAt;
}
