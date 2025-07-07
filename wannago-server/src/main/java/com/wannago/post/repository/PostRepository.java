package com.wannago.post.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wannago.post.entity.Post;


public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT distinct p " +
            "FROM Post p " +
            "JOIN FETCH p.dailySchedules d " +
            "JOIN FETCH d.timeSchedules t " +
            "WHERE p.id = :postId")
    Optional<Post> findByIdWithSchedules(@Param("postId") Long postId);


    Page<Post> findAll(Pageable pageable);

    Page<Post> findAllByAuthor(String loginId, Pageable pageable);
    List<Post> findByAuthorOrderByCreatedDateDesc(String author);

}
