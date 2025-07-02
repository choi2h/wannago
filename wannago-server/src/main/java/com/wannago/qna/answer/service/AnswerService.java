package com.wannago.qna.answer.service;

import com.wannago.qna.answer.dto.AnswerRequest;
import com.wannago.qna.answer.dto.AnswerResponse;



public interface AnswerService {
    AnswerResponse createAnswer(Long askId, String LoginId,AnswerRequest request);

}
