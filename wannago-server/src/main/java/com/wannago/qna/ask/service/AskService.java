package com.wannago.qna.ask.service;


import com.wannago.common.exception.CustomErrorCode;
import com.wannago.common.exception.CustomException;
import com.wannago.member.entity.Member;
import com.wannago.qna.ask.dto.AskRequest;
import com.wannago.qna.ask.dto.AskResponse;
import com.wannago.qna.ask.dto.AsksResponse;
import com.wannago.qna.entity.Ask;
import com.wannago.qna.entity.Category;
import com.wannago.qna.ask.repository.AskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AskService {

    private final AskRepository askRepository;

    // 질문 등록
    @Transactional
    public AskResponse createAsk(AskRequest requestDto, Member member) {
        Category category = Category.getCategory(requestDto.getCategory());
        Ask ask = new Ask(category, requestDto.getTitle(), member.getLoginId(), requestDto.getContent());
        Ask savedAsk = askRepository.save(ask);
        return new AskResponse(savedAsk);
    }

    // 질문 수정
    @Transactional
    public AskResponse updateAsk(Long id, AskRequest requestDto) {
        Ask ask = findAskById(id);

        Category category = Category.getCategory(requestDto.getCategory());
        ask.update(category, requestDto.getTitle(), requestDto.getContent());
        return new AskResponse(ask);
    }

    @Transactional
    public void deleteAsk(Long id) {
        Ask ask = findAskById(id);
        askRepository.delete(ask);
    }

    @Transactional(readOnly = true) // 읽기 전용
    public AsksResponse getAsks(String category) {
        Category categoryEntity = Category.getCategory(category);
        if(categoryEntity == null) {
            throw new CustomException(CustomErrorCode.INVALID_CATEGORY);
        }

        List<Ask> asks;
        if(categoryEntity.equals(Category.ALL)) asks = askRepository.findAll();
        else asks = askRepository.findAllByCategory(categoryEntity);

        AsksResponse response = new AsksResponse();
        asks.forEach(ask -> {
            response.addAskResponse(new AskResponse(ask));
        });

        return response;
    }

    @Transactional(readOnly = true)
    public AskResponse getAsk(Long id) {
        Ask ask = findAskById(id);
        return new AskResponse(ask);
    }

    private Ask findAskById(Long id) {
        return askRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 질문이 없습니다.")
        );
    }
}