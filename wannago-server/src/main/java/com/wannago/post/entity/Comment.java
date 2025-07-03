package com.wannago.post.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "comment") //  MongoDB 컬렉션 이름
public class Comment {
    @Id
    private String id; //  MongoDB는 기본적으로 String 타입의 ObjectId 사용
    private Long postId; // MySQL(Post)의 id가 Long이기 때문에 맞춰줘야 연동 시 타입 충돌 없음
    private String parentId; //null = 일반 댓글, not null = 대댓글
    private String author;
    private String contents;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    @Builder.Default
    private List<Comment> replies = new ArrayList<>();// 답글 리스트

    // 댓글 내용 수정용 메서드
    public void updateContent(String contents) {
        this.contents = contents;
        this.modifiedDate = LocalDateTime.now();
    }

    // 대댓글 추가용 메서드
    public void addReply(Comment reply) {
        if (this.replies == null) {
            this.replies = new ArrayList<>();
        }
        this.replies.add(reply);
        this.modifiedDate = LocalDateTime.now(); // 부모 댓글이 수정된 것으로 간주
    }

}
