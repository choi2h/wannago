package com.wannago.qna.answer.repository;

import com.wannago.qna.entity.Answer;
import com.wannago.qna.entity.Ask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query("SELECT a FROM answer a WHERE a.askId = :ask ORDER BY a.isAccepted DESC, a.createdDate ASC")
    List<Answer> findByAskIdOrderByIsAcceptedDesc(@Param("ask") Ask ask);
}