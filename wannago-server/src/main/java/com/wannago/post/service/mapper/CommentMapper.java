package com.wannago.post.service.mapper;

import com.wannago.member.entity.Member;
import com.wannago.post.dto.CommentRequest;
import com.wannago.post.dto.CommentResponse;
import com.wannago.post.entity.Comment;

import java.time.LocalDateTime;

public class CommentMapper {
    // 요청 -> 엔티티로 변환
    public Comment getComment(Long postId, CommentRequest req, Member member){
        return Comment.builder()
                .postId(postId)
                .parentId(null)
                .author(member.getLoginId().toString())
                .contents(req.getContent().trim())
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();
    }

    // 엔티티 -> dto로 변환
    public CommentResponse getCommentResponse(Comment comment){
        return CommentResponse.builder()
                .id(comment.getId())
                .postId(comment.getPostId())
                .parentId(comment.getParentId())
                .author(comment.getAuthor())
                .contents(comment.getContents())
                .createdDate(comment.getCreatedDate())
                .modifiedDate(comment.getModifiedDate())
                .build();
    }
}
