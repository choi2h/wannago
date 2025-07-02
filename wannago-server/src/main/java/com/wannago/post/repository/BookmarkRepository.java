package com.wannago.post.repository;

import com.wannago.member.entity.Member;
import com.wannago.post.entity.Bookmark;
import com.wannago.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByPostAndMember(Post post, Member member);
    boolean existsByPostAndMember(Post post, Member member);
    boolean existsByPost_IdAndMember_Id(Long postId, Long memberId);
    List<Bookmark> findByPostIdInAndMember(List<Long> postIds, Member member);
}

