package com.wannago.post.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wannago.post.entity.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyPostResponse {
    private Long postId;
    private String title;
    private boolean isPublic;
    private List<String> hashtags;
    private int likeCount;
    private boolean liked;
    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDateTime createdDate;
    
      // [추가] Post 엔티티를 파라미터로 받는 생성자
    public MyPostResponse(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.isPublic = post.isPublic();
        this.createdDate = post.getCreatedDate();
        
        // Post 엔티티의 태그(PostTag 리스트)를 해시태그 이름(String 리스트)으로 변환
        this.hashtags = post.getTags().stream()
                .map(postTag -> postTag.getTag().getName())
                .collect(Collectors.toList());
        
        // likeCount와 liked는 Post 엔티티에 직접적인 정보가 없으므로,
        // 서비스 레이어에서 별도로 계산하여 채워야 합니다. 여기서는 기본값을 설정합니다.
        this.likeCount = 0; // 예시: 실제 좋아요 수는 서비스에서 계산 필요
        this.liked = false; // 예시: 현재 사용자의 좋아요 여부는 서비스에서 계산 필요
    }
}