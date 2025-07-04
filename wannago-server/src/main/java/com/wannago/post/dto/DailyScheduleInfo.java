package com.wannago.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class DailyScheduleInfo {

    @NotBlank
    @Pattern(regexp = "^(\\d+일차$)")
    private String day;

    @Size(min = 1)
    List<TimeScheduleInfo> timeSchedules;

    @Builder
    public DailyScheduleInfo(String day) {
        this.day = day;
        timeSchedules = new ArrayList<>();
    }

    public void addTimeSchedule(TimeScheduleInfo timeSchedule) {
        this.timeSchedules.add(timeSchedule);
    }
}
