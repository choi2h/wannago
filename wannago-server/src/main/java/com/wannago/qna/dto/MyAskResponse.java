package com.wannago.qna.dto;

import java.time.LocalDateTime;

import com.wannago.qna.entity.Ask;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyAskResponse {
    private Long id;
    private String category;
    private String title;
    private String content;
    private boolean isAccepted;
    private LocalDateTime createdDate;

    public static MyAskResponse from(Ask ask) {
        return new MyAskResponse(
                ask.getId(),
                ask.getCategory().name(),
                ask.getTitle(),
                ask.getContents(),
                ask.isAccepted(),
                ask.getCreatedDate()
        );
    }
}
