package com.wannago.post.repository;

import com.wannago.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT distinct p " +
            "FROM Post p " +
            "JOIN FETCH p.dailySchedules d " +
            "JOIN FETCH d.timeSchedules t " +
            "WHERE p.id = :postId")
    Optional<Post> findByIdWithSchedules(@Param("postId") Long postId);
}
