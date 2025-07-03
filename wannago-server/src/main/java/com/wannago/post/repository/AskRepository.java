package com.wannago.post.repository;


import com.wannago.qna.entity.Ask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AskRepository extends JpaRepository<Ask, Long> {
    List<Ask> findByAuthor(String loginId);  // 질문 작성자 기준으로 조회
}
