package com.wannago.post.dto;

import com.wannago.post.entity.Comment;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponse {
    private String id;
    private Long postId;
    private String parentId;
    private String author;
    private String contents;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private List<CommentResponse> replies; // 답글 리스트 (CommentResponse 타입)
}
