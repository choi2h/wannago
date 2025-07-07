package com.wannago.common.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {
    // 회원 M
    MEMBER_NOT_EXIST(HttpStatus.BAD_REQUEST, "M001", "존재하지 않는 회원입니다."),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "M002", "다시 작성해주세요"),
    DUPLICATE_NAME(HttpStatus.BAD_REQUEST, "M003", "사용할 수 없는 아이디입니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "M004", "입력된 비밀번호가 일치하지 않습니다."),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "M005", "사용할 수 없는 이메일입니다."),
    DUPLICATE_LOGINID(HttpStatus.BAD_REQUEST, "M006", "사용할 수 없는 로그인아이디입니다."),
    PASSWORD_NOT_MATCH(HttpStatus.UNAUTHORIZED, "M007", "입력된 비밀번호가 일치하지 않습니다."),
    EMAIL_MISMATCH(HttpStatus.BAD_REQUEST, "M008", "잘못된 이메일입니다."),
    MEMBER_NOT_FOUND(HttpStatus.UNAUTHORIZED,"M009", "회원을 찾을 수 없습니다."),

    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED,"M010", "유효하지 않은 RefreshToken입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED,"M011", "유효하지 않은 토큰입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "M012", "인증이 필요합니다."),
    INVALID_PASSWORD_LENGTH(HttpStatus.BAD_REQUEST, "M013", "비밀번호는 6자 이상 20자 이하로 입력해주세요."),

    // 게시글 P
    POST_NOT_FOUND(HttpStatus.BAD_REQUEST, "P001", "요청하신 게시글을 찾을 수 없습니다."),
    POST_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "P002", "이미 삭제된 게시글입니다."),
    NOT_POST_AUTHOR_FOR_DELETE(HttpStatus.UNAUTHORIZED, "P003", "게시글 삭제 권한이 없습니다."),
    NOT_POST_AUTHOR_FOR_UPDATE(HttpStatus.UNAUTHORIZED, "P004", "게시글 수정 권한이 없습니다."),

    //북마크 B
    ALREADY_BOOKMARKED(HttpStatus.CONFLICT, "B001", "이미 북마크된 게시글입니다."),
    BOOKMARK_NOT_FOUND(HttpStatus.NOT_FOUND, "B002", "북마크된 게시물을 찾을 수 없습니다."),

    // 댓글 C
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "C001", "요청하신 댓글을 찾을 수 없습니다."),
    COMMENT_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "C002", "댓글을 수정할 권한이 없습니다."),

    // 답글 R
    INVALID_COMMENT_OPERATION(HttpStatus.BAD_REQUEST, "R001", "대댓글에는 다시 대댓글을 달 수 없습니다."),

    // 질문 Q
    QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "Q001", "요청하신 질문을 찾을 수 없습니다."),

    // 질문-답변 A
    ANSWER_NOT_FOUND(HttpStatus.NOT_FOUND, "A001", "요청하신 답변을 찾을 수 없습니다."),
    ANSWER_CONTENT_EMPTY(HttpStatus.BAD_REQUEST, "A002", "답변 내용을 작성해주세요."),
    ANSWER_UNAUTHORIZED(HttpStatus.FORBIDDEN, "A003", "답변 작성자만 수정/삭제할 수 있습니다."),
    ANSWER_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "A004", "답변 수정에 실패했습니다. 다시 시도해주세요."),
    ANSWER_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "A005", "답변 삭제에 실패했습니다. 다시 시도해주세요."),
    ANSWER_ALREADY_ACCEPTED(HttpStatus.BAD_REQUEST, "A006", "이미 채택된 답변이 있습니다.");


    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
