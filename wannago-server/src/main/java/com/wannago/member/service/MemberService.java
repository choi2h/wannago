package com.wannago.member.service;

import com.wannago.member.dto.MemberInfoResponse;
import com.wannago.member.dto.MemberUpdateRequest;

public interface MemberService {
    // 내 정보 조회
    MemberInfoResponse getMyInfo(String loginId);

    // 내 정보 수정
    void updateMyInfo(String loginId, MemberUpdateRequest request);
}