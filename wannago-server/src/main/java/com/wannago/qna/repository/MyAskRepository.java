package com.wannago.qna.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wannago.member.entity.Member;
import com.wannago.qna.entity.Ask;

public interface MyAskRepository extends JpaRepository<Ask, Long> {
    
    // 로그인한 사용자가 작성한 질문 전체 조회
    List<Ask> findByAuthor(String loginId);

    // 또는 Member 객체를 기준으로 조회할 수도 있음 (예: 연관관계 매핑이 되어 있는 경우)
    List<Ask> findByMember(Member member);
}
