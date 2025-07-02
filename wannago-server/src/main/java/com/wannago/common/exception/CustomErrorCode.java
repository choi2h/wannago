package com.wannago.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {
    // 회원 M
    MEMBER_NOT_EXIST(HttpStatus.BAD_REQUEST, "M001", "존재하지 않는 회원입니다."),
    DUPLICATE_NAME(HttpStatus.BAD_REQUEST, "M002", "사용할 수 없는 아이디입니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "M003", "입력된 비밀번호가 일치하지 않습니다."),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "M004", "다시 작성해주세요"),
    EMAIL_MISMATCH(HttpStatus.BAD_REQUEST, "M005", "잘못된 이메일입니다."),
    MEMBER_NOT_FOUND(HttpStatus.UNAUTHORIZED,"M006", "회원을 찾을 수 없습니다."),
    PASSWORD_NOT_MATCH(HttpStatus.UNAUTHORIZED, "M007", "입력된 비밀번호가 일치하지 않습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED,"M008", "유효하지 않은 RefreshToken입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED,"M009", "유효하지 않은 토큰입니다."),
    
    // 게시글 P
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "P001", "요청하신 게시글을 찾을 수 없습니다."),

    // 댓글 C
    COMMENT_EMPTY(HttpStatus.BAD_REQUEST, "C001", "댓글 내용을 입력해주세요."),
    COMMENT_TOO_LONG(HttpStatus.BAD_REQUEST, "C002", "댓글은 100자 이하로 입력해주세요.");

    // 답변 A


    // 질문 Q


    // 답글 A - 코드명 겹치지 않게 지정 필요

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}

