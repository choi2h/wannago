package com.wannago.qna.dto;

import lombok.Getter;

@Getter
public class AskRequestDto {
    // 클라이언트가 보내줘야 하는 정보 1: 질문 제목
    private String title;


package com.wannago.dto;

import com.wannago.entity.Ask;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AskResponseDto {
    // 서버가 응답해주는 정보 1: DB에 저장된 후 생성된 고유 ID
    private Long id;

    // 서버가 응답해주는 정보 2: 저장된 질문 제목
    private String title;

    // 서버가 응답해주는 정보 3: 저장된 질문 내용
    private String content;

    // 서버가 응답해주는 정보 4: 질문 생성 시간
    private LocalDateTime createdAt;

    // 서버가 응답해주는 정보 5: 질문 마지막 수정 시간
    private LocalDateTime modifiedAt;

    public AskResponseDto(Ask ask) {
        this.id = ask.getId();
        this.title = ask.getTitle();
        this.content = ask.getContent();
        this.createdAt = ask.getCreatedAt();
        this.modifiedAt = ask.getModifiedAt();
    }
}
