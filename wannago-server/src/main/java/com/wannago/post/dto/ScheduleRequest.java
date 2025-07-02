package com.wannago.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
@AllArgsConstructor
public class ScheduleRequest {
    private String title;
    private String contents;
    private String time;
    private String locationName;
    private BigDecimal lat;
    private BigDecimal lng;
}
