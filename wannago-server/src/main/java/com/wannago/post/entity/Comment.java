package com.wannago.post.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Document("comment")
public class Comment {
    @Id
    private Long id;
    private Long postId;
    private String author;
    private String contents;
    private Long parentId;
    private LocalDateTime createdDate;
}
