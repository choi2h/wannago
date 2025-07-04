package com.wannago.qna.service;

import java.util.List;

import com.wannago.member.entity.Member;
import com.wannago.qna.dto.MyAskResponse;

public interface MyAskService{
    List<MyAskResponse> getQnasByMember(Member member);
}
