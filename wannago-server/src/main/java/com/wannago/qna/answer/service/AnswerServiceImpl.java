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
           // log.error("답변 삭제 실패: answerId={}, author={}, error={}", answerId, LoginId, e.getMessage());
            throw new CustomException(CustomErrorCode.ANSWER_DELETE_FAILED);
        }
    }

    // 답변 작성자와 수정 하려는 사람이 같은 사람인지 검증
    private void validateAnswerAuthor(Answer answer, Long memberId) {
        if (answer.getMemberId() == null || !memberId.equals(answer.getMemberId().getId())) {
            throw new CustomException(CustomErrorCode.ANSWER_UNAUTHORIZED);
        }
    }
}