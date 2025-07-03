package com.wannago.post.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class PostRequest {
    @NotBlank(message = "제목을 입력해주세요.")
    @Size(min=4, max=20, message = "제목은 4자 이상 20자 이하로 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String contents;

    @NotBlank
    private String author;

    private List<String> tags;

    @NotNull @Size(min = 1, message = "일정은 1개이상 입력되어야 합니다.")
    private List<ScheduleRequest> schedules;
}