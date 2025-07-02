package com.wannago.post.service;

import com.wannago.common.exception.CustomErrorCode;
import com.wannago.common.exception.CustomException;
import com.wannago.member.entity.Member;
import com.wannago.post.dto.CommentRequest;
import com.wannago.post.dto.CommentResponse;
import com.wannago.post.entity.Comment;
import com.wannago.post.repository.CommentRepository;
import com.wannago.post.service.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    // 댓글 작성
    @Override
    public CommentResponse addComment(Long postId, CommentRequest req, Member member){
        // 댓글 내용이 빈 값이거나 100자 초과할 경우 예외처리
        if(req.getContent() == null | req.getContent().isBlank()){ // isBlank -> 스페이스만 입력해도 막고 싶을 경우
            throw new CustomException(CustomErrorCode.COMMENT_EMPTY);
        }
        if(req.getContent().length() > 100){
            throw new CustomException(CustomErrorCode.COMMENT_TOO_LONG);
        }
        // 댓글 객체 생성
        Comment comment = commentMapper.getComment(postId,req,member);
        // 댓글 저장
        commentRepository.save(comment);
        // 댓글 DTO 반환
        return commentMapper.getCommentResponse(comment);
    }


}
