package com.wannago.qna.ask.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wannago.qna.entity.Ask;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class AskResponse {
    private final Long id;
    private String loginId;
    private final String category;
    private final String title;
    private final String contents;
    @JsonFormat(pattern = "yyyy.MM.dd")
    private final LocalDateTime createdAt;


    public AskResponse(Ask ask) {
        this.id = ask.getId();
        this.loginId = ask.getMember().getLoginId();
        this.category = ask.getCategory().name();
        this.title = ask.getTitle();
        this.contents = ask.getContents();
        this.createdAt = ask.getCreatedDate();
    }
}
