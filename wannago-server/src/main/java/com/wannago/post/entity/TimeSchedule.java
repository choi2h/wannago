package com.wannago.post.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@Entity @Getter
@Table(name = "time_schedule")
@NoArgsConstructor
public class TimeSchedule {

    @Id
    @Column(name = "time_schedule_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @Setter
    @JoinColumn(name = "daily_id", nullable = false)
    private DailySchedule dailySchedule;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "contents", columnDefinition = "TEXT", nullable = false)
    private String contents;

    @Column(name = "time", nullable = false)
    private LocalTime time;

    @Column(name = "location_name", length = 100, nullable = false)
    private String locationName;

    @Column(name = "lat", precision=10, scale=7,  nullable = false)
    private BigDecimal lat;

    @Column(name = "lng", precision=10, scale=7, nullable = false)
    private BigDecimal lng;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "timeSchedule")
    private List<ScheduleImage> images;

    @Builder
    public TimeSchedule(String title, String contents, LocalTime time, String locationName, BigDecimal lat, BigDecimal lng) {
        this.title = title;
        this.contents = contents;
        this.time = time;
        this.locationName = locationName;
        this.lat = lat;
        this.lng = lng;
    }
}
