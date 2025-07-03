package com.wannago.qna.entity;

import com.wannago.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ask_id")
    private Long id;

    @Column(nullable = false)
    private String category;  // 질문 카테고리

    @Column(nullable = false)
    private String title;     // 질문 제목

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;   // 질문 내용

    @Column(nullable = false)
    private boolean isAccepted; // 채택 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;    // 작성자 - 로그인 정보

    @Column(nullable = false)
    private String author;    // 작성자 loginId 저장용

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;  // 작성일

    @UpdateTimestamp
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;  // 수정일
}
