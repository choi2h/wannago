package com.wannago.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    @NotBlank(message = "댓글 내용은 비어 있을 수 없습니다.")
    @Size(max = 100, message = "댓글은 최대 100자까지 입력할 수 있습니다.")
    private String content;
}
