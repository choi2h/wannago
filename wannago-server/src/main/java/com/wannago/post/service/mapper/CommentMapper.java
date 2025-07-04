package com.wannago.post.service.mapper;

import com.wannago.member.entity.Member;
import com.wannago.post.dto.CommentRequest;
import com.wannago.post.dto.CommentResponse;
import com.wannago.post.entity.Comment;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapper {
    // 요청 -> 엔티티로 변환 (일반 댓글용)
    public Comment getComment(Long postId, CommentRequest req, Member member){
        return Comment.builder()
                .postId(postId)
                .parentId(null) // 댓글은 parentId가 null
                .author(member.getLoginId().toString())
                .contents(req.getContent().trim())
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();
    }

    // 요청 -> 대댓글 엔티티로 변환 (대댓글용 - parentId를 받음)
    public Comment getReply(Long postId, String parentId, CommentRequest req, Member member){
        return Comment.builder()
                .id(new ObjectId().toString())
                .postId(postId)
                .parentId(parentId) // 대댓글은 parentId가 설정됨
                .author(member.getLoginId().toString())
                .contents(req.getContent().trim())
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();
    }

    // Comment 엔티티 -> DTO로 변환 (대댓글 리스트 없이)
    public CommentResponse getCommentResponse(Comment comment){
        return CommentResponse.builder()
                .id(comment.getId())
                .postId(comment.getPostId())
                .parentId(comment.getParentId()) // 대댓글은 parentId가 설정됨
                .author(comment.getAuthor())
                .contents(comment.getContents())
                .createdDate(comment.getCreatedDate())
                .modifiedDate(comment.getModifiedDate())
                .build();
    }

    // 재귀를 사용하여 Comment 엔티티 객체의 트리 구조(댓글과 그에 딸린 모든 대댓글들)를 CommentResponse DTO 객체의 트리 구조로 변환
    //  Comment 객체가 최상위 댓글이든 대댓글이든 상관없이, 그 Comment 객체와 그 안에 포함된 모든 하위 replies들을 CommentResponse DTO로 변환
    public CommentResponse getCommentResponseWithReplies(Comment comment) {
        // 댓글 dto로 변환
        CommentResponse commentResponse = getCommentResponse(comment);
        // 대댓글이 null이 아니라면 => 재귀 호출(대댓글 dto 변환) 시작
        if (comment.getReplies() != null && !comment.getReplies().isEmpty()) {
            List<CommentResponse> replyResponses =
                    // 각 대댓글(Comment 객체)에 대해 재귀 호출!
                    comment.getReplies().stream().map(this::getCommentResponseWithReplies)
                            // 작성 시간순으로 대댓글 정렬
                    .sorted(Comparator.comparing(CommentResponse::getCreatedDate))
                            // 리스트로 변환
                    .collect(Collectors.toList());
            commentResponse.setReplies(replyResponses); // 현재 댓글 DTO에 변환된 대댓글 리스트 설정
        }
        // else => 대댓글이 null이라면 댓글만 dto로 변환한채로 반환
        return commentResponse;
    }
}
