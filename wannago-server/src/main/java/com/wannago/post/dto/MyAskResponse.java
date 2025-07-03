package com.wannago.qna.dto;

import com.wannago.qna.entity.Ask;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AskResponse {
    private Long id;
    private String category;
    private String title;
    private String content;
    private boolean isAccepted;
    private LocalDateTime createdDate;

    public static AskResponse from(Ask ask) {
        return new AskResponse(
                ask.getId(),
                ask.getCategory(),
                ask.getTitle(),
                ask.getContent(),
                ask.isAccepted(),
                ask.getCreatedDate()
        );
    }
}