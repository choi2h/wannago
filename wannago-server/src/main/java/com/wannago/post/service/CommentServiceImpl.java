package com.wannago.post.service;
import com.wannago.common.exception.CustomErrorCode;
import com.wannago.common.exception.CustomException;
import com.wannago.member.entity.Member;
import com.wannago.post.dto.CommentRequest;
import com.wannago.post.dto.CommentResponse;
import com.wannago.post.entity.Comment;
import com.wannago.post.repository.CommentRepository;
import com.wannago.post.service.mapper.CommentMapper;
import jakarta.transaction.Transactional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate; // MongoTemplate 임포트
import org.springframework.data.mongodb.core.query.Criteria; // Criteria 임포트
import org.springframework.data.mongodb.core.query.Query;     // Query 임포트
import org.springframework.data.mongodb.core.query.Update;    // Update 임포트
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final MongoTemplate mongoTemplate;

    // 댓글 작성
    @Override
    public CommentResponse addComment(Long postId, CommentRequest commentRequest, Member member){

        // 댓글 객체 생성( 댓글은 parentId = null)
        Comment comment = commentMapper.getComment(postId,commentRequest,member);

        // 댓글 저장
        commentRepository.save(comment);
        // 댓글 DTO 반환
        return commentMapper.getCommentResponse(comment);
    }

    // 대댓글 작성: 부모 댓글의 replies 리스트에 직접 추가
    @Override
    public CommentResponse addReply(Long postId, String parentId, CommentRequest commentRequest, Member member) {
        // 부모 댓글을 찾아 유효성 검증
        Comment parentComment = getCommentOrThrow(parentId);

        // 대댓글 객체를 생성하여 부모 Document의 Sub-document로 만들기
        Comment reply = commentMapper.getReply(
                parentComment.getPostId(), // 부모 댓글의 postId를 사용
                parentId,
                commentRequest,
                member
        );

        // 부모 댓글의 replies 리스트에 새로 생성된 대댓글을 추가
        parentComment.addReply(reply);

        // 변경된 부모 댓글 문서를 저장 -> MongoDB는 내장된 배열을 업데이트할 때 전체 문서를 저장
        commentRepository.save(parentComment);

        // 대댓글 DTO 반환 (reply는 DB에 독립적으로 저장되지 않은 객체)
        return commentMapper.getCommentResponse(reply);
    }

     // 특정 게시글에 대한 모든 댓글과 대댓글을 함께 조회
    // MongoDB의 내장(Embedded) 구조에서는 일반 댓글 문서를 조회하면 그 안에 모든 대댓글이 이미 포함되어 있음
    @Override
    public List<CommentResponse> getAllCommentsWithReplies(Long postId) {
        // 해당 postId에 속하는 모든 일반 댓글을 조회
        List<Comment> parentComments = commentRepository.findByPostIdAndParentIdIsNullOrderByCreatedDateAsc(postId);

        // 조회된 부모 댓글들을 CommentResponse DTO로 변환, 내장된 replies 리스트의 대댓글들도 CommentResponse DTO로 변환하여 포함
        return parentComments.stream()
                .map(commentMapper::getCommentResponseWithReplies)
                .collect(Collectors.toList());
    }

    // 특정 댓글의 대댓글만 조회
    @Override
    public List<CommentResponse> getRepliesForComment(String parentCommentId) {
        Comment parentComment = getCommentOrThrow(parentCommentId);
        // 대댓글 리스트가 null이 아니라면 반환
        if (parentComment.getReplies() == null || parentComment.getReplies().isEmpty()) {
            return List.of();
        }
        // 대댓글 리스트가 null이라면 가져오기
        return parentComment.getReplies().stream()
                .map(commentMapper::getCommentResponseWithReplies)
                .sorted(Comparator.comparing(CommentResponse::getCreatedDate))
                .collect(Collectors.toList());
    }

    // 댓글 수정
    @Override
    public CommentResponse updateComment(String commentId,CommentRequest commentRequest, Member member){
        // 댓글 찾기 및 유효성 검증
        Comment comment = getCommentOrThrow(commentId);
        // 로그인한 사용자가 해당 댓글의 작성자인지 검증
        validateAuthor(comment,member);
        // 댓글 수정
        comment.updateContent(commentRequest.getContent());
        // 수정된 댓글 저장
        commentRepository.save(comment);
        // 댓글 DTO 반환
        return commentMapper.getCommentResponse(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long postId, String commentId, Member member) {
        // 댓글 유효성 검증 없이 가져오기(대댓글이라면 무조건 예외뜨므로)
        Optional<Comment> comment = commentRepository.findById(commentId);

        /// 댓글인 경우 (parentId가 null)
        if (comment.isPresent() && comment.get().getParentId() == null) {
            // 작성자 유효성 검증
            validateAuthor(comment.get(), member);
            commentRepository.delete(comment.get()); // 내장된 replies도 함께 삭제됨
            return;
        }
        /// 대댓글인 경우: 부모 댓글 문서에서 해당 대댓글만 제거
        // String을 mongodb 쿼리에서 읽을 수 있게 ObjectId로 직접 변환해주기
        ObjectId commentObjectId = new ObjectId(commentId);

        // postId가 1이고, replies 배열 속 id가 ~인 요소를 가진 Comment 문서(최;상위 댓글)를 찾아라
        /// 로직에선 id여도 몽고 db에서 조회하면 _id로 나오기 때문에 이거에 맞춰야 함
        Query query = new Query(Criteria.where("postId").is(postId)
                .and("replies._id").is(commentObjectId));
        Comment parentComment = mongoTemplate.findOne(query, Comment.class);
        // 부모 댓글을 찾을 수 없는 경우
        if (parentComment == null) {
            throw new CustomException(CustomErrorCode.COMMENT_NOT_FOUND);
        }
        // 대댓글 객체 추출 후 작성자 검증
        Comment reply = findCommentInTreeById(parentComment, commentId);
        validateAuthor(reply, member);
        // 실제 삭제 수행
        Update update = new Update().pull("replies", Query.query(Criteria.where("_id").is(commentId)));
        mongoTemplate.updateFirst(query, update, Comment.class);
    }

    // deleteComment 메서드 내에서 `$pull` 연산 전에 대댓글의 작성자를 확인하는 용도로 사용
    private Comment findCommentInTreeById(Comment currentComment, String targetId) {
        if (currentComment.getId().equals(targetId)) {
            return currentComment;
        }
        if (currentComment.getReplies() != null) {
            for (Comment reply : currentComment.getReplies()) {
                Comment found = findCommentInTreeById(reply, targetId); // 재귀 호출
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }


    // 댓글 찾기 및 유효성 검증 유틸 메소드
    private Comment getCommentOrThrow(String commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.COMMENT_NOT_FOUND));
    }

    // 댓글 또는 대댓글의 작성자가 현재 로그인 사용자와 같은지 검증하는 유틸 메소드
    private void validateAuthor(Comment comment, Member member) {
        if (comment == null || !comment.getAuthor().equals(member.getLoginId().toString())) {
            throw new CustomException(CustomErrorCode.COMMENT_UNAUTHORIZED);
        }
    }
}
