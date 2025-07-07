package com.wannago.qna.ask.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AskRequest {
    private String category;
    private String title;
    private String contents;
}
