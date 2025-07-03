package com.wannago.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder @Getter
@AllArgsConstructor
public class ScheduleInfo {
    private String title;
    private String contents;
    private String time;
    private String locationName;
    private BigDecimal lat;
    private BigDecimal lng;
}
