package com.wannago.post.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity @Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "author", length = 30, nullable = false)
    private String author;

    @Column(name = "contents", columnDefinition = "TEXT", nullable = false)
    private String contents;

    @Column(name = "is_public", columnDefinition = "TINYINT(1)", nullable = false)
    private boolean isPublic;

    @CreationTimestamp
    @Column(name = "created_date", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "modified_date", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime modifiedDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post", orphanRemoval = true)
    private Set<DailySchedule> dailySchedules;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PostTag> tags;

    @Builder
    public Post(String author, String title, String contents, boolean isPublic) {
        this.author = author;
        this.title = title;
        this.contents = contents;
        this.isPublic = isPublic;
        this.dailySchedules = new HashSet<>();
        this.tags = new ArrayList<>();
    }

    public void addSchedule(DailySchedule dailySchedule) {
        dailySchedule.setPost(this);
        this.dailySchedules.add(dailySchedule);
    }

    public void addTag(Tag tag) {
        tags.add(PostTag.builder().post(this).tag(tag).build());
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContents(String contents) {
        this.contents = contents;
    }

    public void clearSchedules() {
        this.dailySchedules.clear();
    }

    public void clearTags() {
        this.tags.clear();
    }
}
