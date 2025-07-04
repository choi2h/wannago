package com.wannago.qna.answer.service;

import com.wannago.qna.answer.dto.AnswerResponse;

import java.util.List;

public interface AnswerService {
    List<AnswerResponse> getAnswersByAskId(Long askId);
}
