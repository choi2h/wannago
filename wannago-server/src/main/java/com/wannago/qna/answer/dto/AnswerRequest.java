package com.wannago.qna.answer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class AnswerRequest{
    private Long askId;
    private String contents;
}