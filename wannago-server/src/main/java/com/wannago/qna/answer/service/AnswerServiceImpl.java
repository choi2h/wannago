package com.wannago.qna.answer.service;

import com.wannago.member.entity.Member;
import com.wannago.qna.answer.dto.AnswerRequest;
import com.wannago.qna.answer.repository.AnswerRepository;
import com.wannago.member.repository.MemberRepository;
import com.wannago.common.exception.CustomErrorCode;
import com.wannago.common.exception.CustomException;
import com.wannago.qna.answer.dto.AnswerResponse;
import com.wannago.qna.ask.entity.Answer;
import com.wannago.qna.ask.entity.Ask;
import com.wannago.qna.ask.repository.AskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;


//@Slf4j 로그 사용시
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnswerServiceImpl implements AnswerService {

    private final AskRepository askRepository;
    private final AnswerRepository answerRepository;
    private final MemberRepository memberRepository;

    // 답변 수정
    @Transactional
    public AnswerResponse updateAnswer(Long answerId, String LoginId , AnswerRequest request)  {
        // 답변 내용 검증(답변이 비어 있는지)
        validateAnswerContent(request.getContents());
        // 답변 존재 여부 확인 및 해당 답변 객체 가져오기
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.ANSWER_NOT_FOUND));
        // 작성자 권한 확인
        Member member = memberRepository.findByLoginId(LoginId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_EXIST));
        validateAnswerAuthor(answer, member.getId());

        try {
            // 답변 내용 수정
            answer.updateContents(request.getContents());
            Answer updatedAnswer = answerRepository.save(answer);

            //log.info("답변 수정 완료: answerId={}, author={}", answerId, currentUser);
            return convertToResponse(updatedAnswer);

        } catch (Exception e) {
            //log.error("답변 수정 실패: answerId={}, author={}, error={}", answerId, currentUser, e.getMessage());
            throw new CustomException(CustomErrorCode.ANSWER_UPDATE_FAILED);
        }
    }

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

    // 특정 질문의 모든 답변 조회
    public List<AnswerResponse> getAnswersByAskId(Long askId) {
        Ask ask = askRepository.findById(askId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.QUESTION_NOT_FOUND));
        List<Answer> answers = answerRepository.findByAskIdOrderByIsAcceptedDesc(ask);
      
         return answers.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
       
    // 답변 채택
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