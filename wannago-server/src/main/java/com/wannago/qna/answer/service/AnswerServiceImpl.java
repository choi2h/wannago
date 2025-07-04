package com.wannago.qna.answer.service;

import com.wannago.qna.answer.repository.AnswerRepository;
import com.wannago.qna.question.repository.AskRepository;
import com.wannago.common.exception.CustomErrorCode;
import com.wannago.common.exception.CustomException;
import com.wannago.qna.answer.dto.AnswerResponse;
import com.wannago.qna.entity.Answer;
import com.wannago.qna.entity.Ask;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

//@Slf4j 로그 사용시
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final AskRepository askRepository;

    // 특정 질문의 모든 답변 조회
    public List<AnswerResponse> getAnswersByAskId(Long askId) {
        Ask ask = askRepository.findById(askId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.QUESTION_NOT_FOUND));

        List<Answer> answers = answerRepository.findByAskIdOrderByIsAcceptedDesc(ask);

        return answers.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Entity를 DTO로 변환
    private AnswerResponse convertToResponse(Answer answer) {
        return AnswerResponse.builder()
                .answerId(answer.getId())
                .author(answer.getAuthor())
                .contents(answer.getContents())
                .isAccepted(answer.isAccepted())
                .createdDate(answer.getCreatedDate())
                .modifiedDate(answer.getModifiedDate())
                .build();
    }
}