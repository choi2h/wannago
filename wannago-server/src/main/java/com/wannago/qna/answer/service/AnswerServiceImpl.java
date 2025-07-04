package com.wannago.qna.answer.service;

import com.wannago.member.entity.Member;
import com.wannago.member.repository.MemberRepository;
import com.wannago.qna.answer.repository.AnswerRepository;
import com.wannago.common.exception.CustomErrorCode;
import com.wannago.common.exception.CustomException;
import com.wannago.qna.entity.Answer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//@Slf4j 로그 사용시
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnswerServiceImpl implements AnswerService {

    private final AskRepository askRepository;
    private final AnswerRepository answerRepository;
    private final MemberRepository memberRepository;

    // 답변 삭제
    @Transactional
    public void deleteAnswer(Long answerId, String LoginId) {
        // 답변 존재 여부 확인
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.ANSWER_NOT_FOUND));

        // 작성자 권한 확인
        Member member = memberRepository.findByLoginId(LoginId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_EXIST));
        validateAnswerAuthor(answer, member.getId());

        try {
            answerRepository.delete(answer);
           // log.info("답변 삭제 완료: answerId={}, author={}", answerId, LoginId);

        } catch (Exception e) {
            //log.error("답변 수정 실패: answerId={}, author={}, error={}", answerId, currentUser, e.getMessage());
            throw new CustomException(CustomErrorCode.ANSWER_UPDATE_FAILED);
        }
    }

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

    @Transactional
    public AnswerResponse acceptAnswer(Long answerId, String LoginId) {
        // 답변 존재 여부 확인 및 해당 답변 객체 가져오기
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.ANSWER_NOT_FOUND));

        // 이미 채택된 답변이 있는지 확인
        if (answerRepository.existsByAskIdAndIsAcceptedTrue(answer.getAskId())) {
            throw new CustomException(CustomErrorCode.ANSWER_ALREADY_ACCEPTED);
        }

        // 작성자 권한 확인
        Member member = memberRepository.findByLoginId(LoginId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_EXIST));
        validateAnswerAuthor(answer, member.getId());

        // 답변 채택 처리
        answer.accept();
        Answer acceptedAnswer = answerRepository.save(answer);

       // log.info("답변 채택 완료: answerId={}, questionAuthor={}", answerId, currentUser);
        return convertToResponse(acceptedAnswer);
    }
    // 답변 내용 검사
    private void validateAnswerContent(String contents) {
        if (!StringUtils.hasText(contents) || contents.trim().isEmpty()) {
            throw new CustomException(CustomErrorCode.ANSWER_CONTENT_EMPTY);
        }
    }

    // 답변 작성자와 수정 하려는 사람이 같은 사람인지 검증
    private void validateAnswerAuthor(Answer answer, Long memberId) {
        if (answer.getMemberId() == null || !memberId.equals(answer.getMemberId().getId())) {
            throw new CustomException(CustomErrorCode.ANSWER_UNAUTHORIZED);
        }
    }
}