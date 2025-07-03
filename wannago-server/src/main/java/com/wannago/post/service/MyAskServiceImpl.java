package com.wannago.qna.service;

import com.wannago.member.entity.Member;
import com.wannago.qna.dto.AskResponse;
import com.wannago.qna.entity.Ask;
import com.wannago.qna.mapper.AskMapper;
import com.wannago.qna.repository.AskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AskServiceImpl implements AskService {

    private final AskRepository askRepository;
    private final AskMapper askMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AskResponse> getQnasByMember(Member member) {
        List<Ask> myQnas = askRepository.findByMember(member);
        return askMapper.toAskResponseList(myQnas);
    }
}
