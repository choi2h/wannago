// DtoAskRequest.java
package com.wannago.dto;

import lombok.Getter;

@Getter
public class AskRequestDto {
    private String title;
    private String content;
}
```java
// AskResponseDto.java
package com.wannago.dto;

import com.wannago.entity.Ask;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class AskResponseDto {
    private Long id;
    private String title;
    private String content;
    // private LocalDateTime createdAt; // Timestamped를 추가하면 주석 해제
    // private LocalDateTime modifiedAt; // Timestamped를 추가하면 주석 해제

    public AskResponseDto(Ask ask) {
        this.id = ask.getId();
        this.title = ask.getTitle();
        this.content = ask.getContent();
        // this.createdAt = ask.getCreatedAt();
        // this.modifiedAt = ask.getModifiedAt();
    }
}
