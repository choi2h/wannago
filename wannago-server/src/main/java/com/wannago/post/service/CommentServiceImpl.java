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
    public CommentResponse addComment(Long postId, CommentRequest commentRequest, Member member){
        // 댓글 객체 생성( 댓글은 parentId = null)
        Comment comment = commentMapper.getComment(postId,null,commentRequest,member);
        // 댓글 저장
        commentRepository.save(comment);
        // 댓글 DTO 반환
        return commentMapper.getCommentResponse(comment);
    }

    // 답글 작성: 부모 댓글의 replies 리스트에 직접 추가
    @Override
    public CommentResponse addReply(Long postId, String parentId, CommentRequest commentRequest, Member member) {
        // 부모 댓글을 찾아 유효성 검증
        Comment parentComment = getCommentOrThrow(parentId);

        // 답글 객체를 생성하여 부모 Document의 Sub-document로 만들기
        Comment reply = commentMapper.getComment(
                parentComment.getPostId(), // 부모 댓글의 postId를 사용
                parentComment.getId(),     // 대댓글 객체에 부모 ID를 설정 (필요에 따라 DTO 변환 시 사용)
                commentRequest,
                member
        );

        // 부모 댓글의 replies 리스트에 새로 생성된 답글을 추가
        parentComment.addReply(reply);

        // 변경된 부모 댓글 문서를 저장 -> MongoDB는 내장된 배열을 업데이트할 때 전체 문서를 저장
        commentRepository.save(parentComment);

        // 답글 DTO 반환 (reply는 DB에 독립적으로 저장되지 않은 객체)
        return commentMapper.getCommentResponse(reply);
    }


    // 댓글 수정
    public CommentResponse updateComment(String commentId,CommentRequest commentRequest, Member member){
        // 댓글 찾기 및 유효성 검증
        Comment comment = getCommentOrThrow(commentId);
        // 로그인한 사용자가 해당 댓글의 작성자인지 검증
        if (!comment.getAuthor().equals(member.getLoginId().toString())) {
            throw new CustomException(CustomErrorCode.COMMENT_UNAUTHORIZED);
        }
        // 댓글 수정
        comment.updateContent(commentRequest.getContent());
        // 수정된 댓글 저장
        commentRepository.save(comment);
        // 댓글 DTO 반환
        return commentMapper.getCommentResponse(comment);
    }

    // 댓글 찾기 및 유효성 검증 유틸 메소드
    private Comment getCommentOrThrow(String commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.COMMENT_NOT_FOUND));
    }
}
