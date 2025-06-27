package com.wannago.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "member")
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "login_id", length = 30, nullable = false, unique = true)
    private String loginId;

    @Column(name = "password", length = 500, nullable = false)
    private String password;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @CreationTimestamp
    @Column(name = "created_date", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "modified_date", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime modifiedDate;

}
