package com.wannago.qna.answer.service;

import com.wannago.qna.answer.dto.AnswerRequest;
import com.wannago.qna.answer.dto.AnswerResponse;

import java.util.List;

public interface AnswerService {
    AnswerResponse updateAnswer(Long answerId,String LoginId, AnswerRequest request);
    AnswerResponse createAnswer(Long askId, String LoginId,AnswerRequest request);
    List<AnswerResponse> getAnswersByAskId(Long askId);
}
