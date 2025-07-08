package com.wannago.member.repository;

import com.wannago.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByLoginId(String loginId);
    boolean existsByName(String name);
    boolean existsByEmail(String email);
    boolean existsByLoginId(String loginId);
}
