package com.wannago.post.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity @Getter
@Table(name = "daily_schedule")
@NoArgsConstructor
public class DailySchedule {
    @Id
    @Column(name = "daily_schedule_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @Setter
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "day")
    private String day;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dailySchedule", fetch = FetchType.LAZY)
    private Set<TimeSchedule> timeSchedules;

    @Builder
    public DailySchedule(String day) {
        this.day = day;
        this.timeSchedules = new HashSet<>();
    }

    public void addTimeSchedule(TimeSchedule timeSchedule) {
        timeSchedule.setDailySchedule(this);
        timeSchedules.add(timeSchedule);
    }
}
