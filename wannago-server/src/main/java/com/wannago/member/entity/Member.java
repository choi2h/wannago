package com.wannago.member.entity;

import java.time.LocalDate; // jakarta.persistence 사용 확인
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter 
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "member") // JPA 엔티티 이름
@Table(name = "member") // 실제 DB 테이블 이름 (name = "member")
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

    @Column(name = "phone_number", length = 20, nullable = true) // 전화번호 길이 및 null 허용 여부 설정
    private String phoneNumber;

    @CreationTimestamp
    @Column(name = "created_date", columnDefinition = "TIMESTAMP", nullable = false, updatable = false) // updatable = false 추가
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "modified_date", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime modifiedDate;

    // 기존 updateInfo 메서드는 그대로 유지
    public void updateInfo(String name, String encodedPassword, String email) {
        this.name = name;
        this.password = encodedPassword;
        this.email = email;
    }

    public void updatePassword(String newEncodedPassword) {
        if (newEncodedPassword != null && !newEncodedPassword.isBlank()) { // null 또는 공백이 아닌 경우에만 업데이트
            this.password = newEncodedPassword;
        }
    }

    public void updatePhoneNumber(String newPhoneNumber) {
        // null 값도 허용하거나, 빈 문자열로 초기화할 수 있도록 설정
        this.phoneNumber = newPhoneNumber;
    }
}