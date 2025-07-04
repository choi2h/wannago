package com.wannago.qna.answer.repository;

import com.wannago.qna.entity.Answer;
import com.wannago.qna.entity.Ask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    // 특정 질문에 채택된 답변이 있는지 확인
    boolean existsByAskIdAndIsAcceptedTrue(Ask ask);

    // 특정 질문의 채택된 답변 조회
    Optional<Answer> findByAskIdAndIsAcceptedTrue(Ask ask);
}