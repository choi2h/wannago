// DtoAskRequest.java
package com.wannago.qna.dto;

import lombok.Getter;

@Getter
public class AskRequestDto {

// DtoAskResponse.java
package com.wannago.dto;

import com.wannago.entity.Ask;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class AskResponseDto {
    private Long id;
    private String title;
    private String content;

    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDateTime modifiedAt;

    public AskResponseDto(Ask ask) {
        this.id = ask.getId();
        this.title = ask.getTitle();
        this.content = ask.getContent();
        this.createdAt = ask.getCreatedAt();
        this.modifiedAt = ask.getModifiedAt();
    }
}
