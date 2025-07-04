package com.wannago.qna.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wannago.member.entity.Member;
import com.wannago.qna.dto.MyAskResponse;
import com.wannago.qna.entity.Ask;
import com.wannago.qna.repository.MyAskRepository;
import com.wannago.qna.service.mapper.MyAskMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyAskServiceImpl implements MyAskService {

    private final MyAskRepository myaskRepository;
    private final MyAskMapper myaskMapper;

    @Override
    @Transactional(readOnly = true)
    public List<MyAskResponse> getQnasByMember(Member member) {
        List<Ask> myQnas = myaskRepository.findByMember(member);
        return myaskMapper.toAskResponseList(myQnas);
    }
}



