package com.wannago.post.repository;

import com.wannago.member.entity.Member;
import com.wannago.post.dto.PostLikeCount;
import com.wannago.post.dto.PostsResponse;
import com.wannago.post.entity.Post;
import com.wannago.post.entity.PostLike;
import jakarta.persistence.MapKeyColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    // 특정 게시글에 대해 사용자가 좋아요 토글을 눌렀는지 여부에 따라 Optional 객체를 통해 결과 반환
    Optional<PostLike> findByPostAndMember(Post post, Member member);

    // 사용자가 게시글에 좋아요 눌렀는지를 빠르게 판단하여, 존재 여부만 반환
    boolean existsByPostAndMember(Post post, Member member);

    boolean existsByPost_IdAndMember_Id(Long postId, Long memberId);

    // 단일 게시글의 좋아요 수 조회
    int countByPost(Post post);
    int countByPost_Id(Long postId);

    // 여러 게시글의 좋아요 수 한 번에 조회
    @Query("SELECT pl.post.id AS postId, COUNT(pl) AS likeCount " +
            "FROM PostLike pl WHERE pl.post.id IN :postIds GROUP BY pl.post.id")
    List<PostLikeCount> countLikesByPostIds(@Param("postIds") List<Long> postIds);


    // 특정 유저가 좋아요 누른 게시글의 좋아요 객체 정보 전체 조회
    List<PostLike> findByPostIdInAndMember(List<Long> postIds, Member member);
}
