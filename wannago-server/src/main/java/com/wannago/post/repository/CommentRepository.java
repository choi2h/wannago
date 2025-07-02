package com.wannago.post.repository;

import com.wannago.post.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment,String> {
    // 특정 게시글에 달린 댓글(최상위 댓글)만 작성시간순으로 정렬해서 가져오기
    List<Comment> findByPostIdAndParentIdIsNullOrderByCreatedDateAsc(String postId);
    // 특정 댓글에 달린 대댓글들 시간순으로 정렬해서 가져오기
    List<Comment> findByParentIdOrderByCreatedDateAsc(String parentId);
}
