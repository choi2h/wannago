package com.wannago.service;

import com.wannago.dto.AskRequestDto;
import com.wannago.dto.AskResponseDto;
import com.wannago.entity.Ask;
import com.wannago.repository.AskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AskService {

    private final AskRepository askRepository;

    // 질문 등록
    @Transactional
    public AskResponseDto createAsk(AskRequestDto requestDto) {
        Ask ask = new Ask(requestDto);
        Ask savedAsk = askRepository.save(ask);
        return new AskResponseDto(savedAsk);
    }

    // 질문 수정
    @Transactional
    public AskResponseDto updateAsk(Long id, AskRequestDto requestDto) {
        // 1. ID로 DB에서 질문을 찾아옵니다. 없으면 예외가 발생합니다.
        Ask ask = findAskById(id);

        // 2. 찾아온 질문 객체의 내용을 DTO의 내용으로 업데이트합니다.
        ask.update(requestDto);

        // 3. @Transactional에 의해 메서드가 끝나면 변경된 내용이 DB에 자동으로 반영됩니다.
        //    그리고 변경된 객체를 DTO로 변환하여 반환합니다.
        return new AskResponseDto(ask);
    }

    // 질문 삭제
    @Transactional
    public void deleteAsk(Long id) {
        // 1. ID로 질문을 찾아옵니다. 없으면 예외가 발생합니다.
        Ask ask = findAskById(id);
        // 2. JpaRepository가 기본으로 제공하는 delete() 메서드를 호출하여 DB에서 삭제합니다.
        askRepository.delete(ask);
    }

    // 질문 목록 조회
    @Transactional(readOnly = true) // 읽기 전용
    public List<AskResponseDto> getAsks() {
        // 내림차순조회
        return askRepository.findAllByOrderByModifiedAtDesc().stream()
                .map(AskResponseDto::new)
                .collect(Collectors.toList());
    }

    // 특정 질문 조회
    @Transactional(readOnly = true)
    public AskResponseDto getAsk(Long id) {
        // findAskById 메서드를 호출하여 ID에 해당하는 Ask 엔티티를 찾습니다.
        Ask ask = findAskById(id);
        // 찾은 엔티티를 DTO로 변환하여 반환합니다.
        return new AskResponseDto(ask);
    }

    // ID로 질문을 찾는 중복 로직을 별도의 메서드로 분리합니다. (이 부분도 추가합니다)
    // 앞으로 수정, 삭제 기능에서도 계속 사용되므로, 코드 재사용성을 높일 수 있습니다.
    private Ask findAskById(Long id) {
        // findById: JpaRepository가 기본으로 제공하는 메서드. ID로 엔티티를 찾아 Optional로 반환합니다.
        // orElseThrow: Optional 객체에 값이 있으면 그 값을 반환하고, 없으면(null이면) 예외를 발생시킵니다.
        return askRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 질문이 없습니다.")
        );
    }
}