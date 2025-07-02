package com.wannago.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
@AllArgsConstructor
public class ScheduleRequest {
    @NotBlank(message = "일정의 제목을 입력해주세요.")
    @Size(min=4, max=20, message = "제목은 4자 이상 20자 이하로 입력해주세요.")
    private String title;
    @NotBlank(message = "내용을 입력해주세요.")
    private String contents;

    @NotBlank
    @Pattern(regexp = "^([01]?\\d|2[0-3]):([0-5]\\d)$",
            message = "시간은 HH:mm 형식이어야 하며, 00:00 ~ 23:59 범위여야 합니다.")
    private String time;

    @NotBlank(message = "일정 장소를 입력해주세요.")
    private String locationName;

    @NotBlank(message = "일정 장소를 입력해주세요.")
    private BigDecimal lat;

    @NotBlank(message = "일정 장소를 입력해주세요.")
    private BigDecimal lng;
}
