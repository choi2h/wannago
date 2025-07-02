package com.wannago.qna.answer.service;

import com.wannago.member.entity.Member;
import com.wannago.member.repository.MemberRepository;
import com.wannago.qna.answer.repository.AnswerRepository;
import com.wannago.common.exception.CustomErrorCode;
import com.wannago.common.exception.CustomException;
import com.wannago.qna.answer.dto.AnswerRequest;
import com.wannago.qna.answer.dto.AnswerResponse;
import com.wannago.qna.answer.repository.AskRepository;
import com.wannago.qna.entity.Answer;
//import com.wannago.qna.repository.AskRepository;
import com.wannago.qna.entity.Ask;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

//@Slf4j 로그 사용시
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnswerServiceImpl {

    private final AnswerRepository answerRepository;
    private final MemberRepository memberRepository;
    private final AskRepository askRepository;

    // 답변 등록
    @Transactional
    public AnswerResponse createAnswer(Long askId, String LoginId, AnswerRequest request) {
        // 답변 내용 검증(답변이 비어있는지)
        validateAnswerContent(request.getContents());

        Ask ask = askRepository.findById(askId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.QUESTION_NOT_FOUND));

        Member member = memberRepository.findByLoginId(LoginId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_EXIST));

        // 답변 생성
        Answer answer = Answer.builder()
                .askId(ask)
                .memberId(member)
                .author(member.getName())
                .contents(request.getContents())
                .isAccepted(false)
                .build();
        Answer savedAnswer = answerRepository.save(answer);

        //로그 사용 시
        //log.info("답변 등록 완료: answerId={}, askId={}, author={}", savedAnswer.getId(), askId, author);

        return convertToResponse(savedAnswer);
    }

    // 답변 내용 검사
    private void validateAnswerContent(String contents) {
        if (!StringUtils.hasText(contents) || contents.trim().isEmpty()) {
            throw new CustomException(CustomErrorCode.ANSWER_CONTENT_EMPTY);
        }
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






