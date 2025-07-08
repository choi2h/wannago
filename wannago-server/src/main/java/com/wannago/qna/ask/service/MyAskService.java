package com.wannago.qna.ask.service;

import com.wannago.member.entity.Member;
import com.wannago.qna.ask.dto.AskResponse;
import com.wannago.qna.ask.dto.AsksResponse;
import com.wannago.qna.ask.repository.AskRepository;
import com.wannago.qna.entity.Ask;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyAskService {

    private final AskRepository askRepository;

    public AsksResponse getAsksByAuthor(Member member) {
        // 1단계에서 추가한 Repository 메소드를 호출합니다.
        List<Ask> asks = askRepository.findAllByAuthorOrderByIdDesc(member.getLoginId());

        // DTO로 변환하여 반환합니다.
        AsksResponse response = new AsksResponse();
        asks.forEach(ask -> response.addAskResponse(new AskResponse(ask)));
        return response;
    }
}