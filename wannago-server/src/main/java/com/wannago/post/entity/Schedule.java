package com.wannago.post.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "schedule")
public class Schedule {

    @Id
    @Column(name = "schedule_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @Setter
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

    @Column(name = "lat", precision=10, scale=7,  nullable = false)
    private BigDecimal lat;

    @Column(name = "lng", precision=10, scale=7, nullable = false)
    private BigDecimal lng;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "schedule")
    private List<ScheduleImage> images;
}
