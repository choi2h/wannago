package com.wannago.qna.answer.service;

import com.wannago.member.entity.Member;
import com.wannago.qna.answer.dto.AnswerRequest;
import com.wannago.qna.answer.dto.AnswerResponse;
import java.util.List;

public interface AnswerService {
    List<AnswerResponse> getAnswersByAskId(Long askId);
    AnswerResponse updateAnswer(Long answerId,String LoginId, AnswerRequest request);
    AnswerResponse createAnswer(Long askId, String LoginId, AnswerRequest request);
    AnswerResponse acceptAnswer(Long askId, Long answerId, Member member);
    void deleteAnswer(Long answerId, String LoginId);
}
