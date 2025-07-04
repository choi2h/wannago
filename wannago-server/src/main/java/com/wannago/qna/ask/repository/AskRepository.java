package com.wannago.qna.ask.repository;

import com.wannago.qna.entity.Ask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AskRepository extends JpaRepository<Ask, Long> {
    List<Ask> findAll();
}
