package com.wannago.qna.ask.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wannago.qna.ask.entity.Ask;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class AskResponse {
    private Long id;
    private String title;
    private String content;

    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDateTime modifiedAt;

    public AskResponse(Ask ask) {
        this.id = ask.getId();
        this.title = ask.getTitle();
        this.content = ask.getContents();
        this.createdAt = ask.getCreatedDate();
        this.modifiedAt = ask.getModifiedDate();
    }
}
