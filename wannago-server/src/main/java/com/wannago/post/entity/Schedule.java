package com.wannago.post.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "schedule")
public class Schedule {

    @Id
    @Column(name = "schedule_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "contents", columnDefinition = "TEXT", nullable = false)
    private String contents;

    @Column(name = "time", nullable = false)
    private LocalTime time;

    @Column(name = "location_name", length = 100, nullable = false)
    private String locationName;

    @Column(name = "lat", nullable = false)
    private BigDecimal lat;

    @Column(name = "log", nullable = false)
    private BigDecimal log;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "schedule")
    private List<ScheduleImage> images;
}
