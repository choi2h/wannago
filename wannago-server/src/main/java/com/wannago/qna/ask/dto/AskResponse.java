package com.wannago.qna.ask.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wannago.qna.entity.Ask;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class AskResponse {
    private final Long id;
    private final String loginId;
    private final String category;
    private final String author;
    private final String title;
    private final String contents;
    @JsonFormat(pattern = "yyyy.MM.dd")
    private final LocalDateTime createdDate;


    public AskResponse(Ask ask) {
        this.id = ask.getId();
        this.loginId = ask.getAuthor();
        this.category = ask.getCategory().name();
        this.title = ask.getTitle();
        this.author = ask.getAuthor();
        this.contents = ask.getContents();
        this.createdDate = ask.getCreatedDate();
    }
}
