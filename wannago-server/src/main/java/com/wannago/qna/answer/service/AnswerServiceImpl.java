package com.wannago.qna.answer.service;
import com.wannago.member.entity.Member;
import com.wannago.member.repository.MemberRepository;
import com.wannago.qna.answer.repository.AnswerRepository;
import com.wannago.common.exception.CustomErrorCode;
import com.wannago.common.exception.CustomException;
import com.wannago.qna.answer.dto.AnswerRequest;
import com.wannago.qna.answer.dto.AnswerResponse;
import com.wannago.qna.entity.Answer;
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

    // 답변 수정
    @Transactional
    public AnswerResponse updateAnswer(Long answerId, String LoginId ,AnswerRequest request)  {
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






