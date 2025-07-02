package com.wannago.post.service;

import com.wannago.member.entity.Member;
import com.wannago.post.dto.CommentRequest;
import com.wannago.post.dto.CommentResponse;

public interface CommentService {
    CommentResponse addComment(Long postId, CommentRequest commentRequest, Member member);
    CommentResponse updateComment(String commentId,CommentRequest commentRequest, Member member);
    CommentResponse addReply(Long postId, String parentId, CommentRequest commentRequest, Member member);
}
