package com.wannago.qna.answer.service;

public interface AnswerService {
    AnswerResponse updateAnswer(Long answerId,String LoginId, AnswerRequest request);
    AnswerResponse createAnswer(Long askId, String LoginId,AnswerRequest request);
    AnswerResponse acceptAnswer(Long answerId, String LoginId);
    void deleteAnswer(Long answerId, String LoginId);
}
