package com.wannago.qna.answer.repository;

import com.wannago.qna.entity.Ask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AskRepository extends JpaRepository<Ask,Long> {
}
