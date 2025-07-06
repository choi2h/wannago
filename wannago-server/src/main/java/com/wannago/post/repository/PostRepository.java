package com.wannago.post.repository;

import com.wannago.post.dto.PostWithLikeCount;
import com.wannago.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    Page<Post> findAll(Pageable pageable);

    Page<Post> findAllByAuthor(String loginId, Pageable pageable);

    @Query("SELECT new com.wannago.post.dto.PostWithLikeCount(p, COUNT(pl)) FROM Post p " +
            "LEFT JOIN PostLike pl ON p.id = pl.post.id " +
            "GROUP BY p.id ORDER BY COUNT(pl) DESC")
    Page<PostWithLikeCount> findAllByLikeCount(Pageable pageable);
}
