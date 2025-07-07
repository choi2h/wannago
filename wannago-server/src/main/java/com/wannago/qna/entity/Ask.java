package com.wannago.qna.entity;

import com.wannago.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ask")
@EntityListeners(AuditingEntityListener.class)
public class Ask {

    @Id
    @Column(name = "ask_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Column(name = "author", length = 30, nullable = false)
    private String author;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "contents", columnDefinition = "TEXT", nullable = false)
    private String contents;

    @Column(name = "is_accepted", columnDefinition = "TINYINT(1)", nullable = false)
    private boolean isAccepted;

    @CreationTimestamp
    @Column(name = "created_date", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "modified_date", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime modifiedDate;

    // 필드 값 변경을 위한 update 메서드 추가
    public void update(Category category, String title, String contents) {
        this.category = category;
        this.title = title;
        this.contents = contents;
    }

    @Builder
    public Ask(Member member, Category category, String title, String author, String contents) {
        this.member = member;
        this.category = category;
        this.title = title;
        this.author = author;
        this.contents = contents;
        this.isAccepted = false;
    }

    public void accept(){
        this.isAccepted = true;
    }
}
