package com.wannago.post.repository;

import com.wannago.post.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment,String> {
    // 특정 postId에 속하는 일반 댓글 (parentId가 null)을 생성일 기준 오름차순으로 조회
    // 내장 구조에서는 parentId로 대댓글을 직접 찾는 메서드는 필요 없음
    List<Comment> findByPostIdAndParentIdIsNullOrderByCreatedDateAsc(Long postId);

}
