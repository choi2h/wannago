package com.wannago.post.repository;

import com.wannago.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p join fetch p.schedules where p.id = :postId")
    Optional<Post> findByIdWithSchedules(@Param("postId") Long postId);


}
