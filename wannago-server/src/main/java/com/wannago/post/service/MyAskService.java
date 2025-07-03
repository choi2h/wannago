package com.wannago.qna.service;

import com.wannago.member.entity.Member;
import com.wannago.qna.dto.AskResponse;

import java.util.List;

public interface AskService {
    List<AskResponse> getQnasByMember(Member member);
}