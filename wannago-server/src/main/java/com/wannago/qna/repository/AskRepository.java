package com.wannago.qna.repository;

import com.wannago.entity.Ask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AskRepository extends JpaRepository<Ask, Long> {
    List<Ask> findAllByOrderByModifiedAtDesc();
}
