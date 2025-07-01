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

    // 인증 A
    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "A001", "로그인이 필요한 요청입니다."),

    // 게시글 P
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "P001", "요청하신 게시글을 찾을 수 없습니다.");


    // 답변 C


    // 질문 Q


    // 답글 A

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}

