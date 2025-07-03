package com.wannago.post.service.mapper;

import com.wannago.post.dto.PostRequest;
import com.wannago.post.dto.PostResponse;
import com.wannago.post.dto.PostsResponse;
import com.wannago.post.entity.Post;
import com.wannago.qna.entity.Ask;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PostMapper {
    // 요청 → 엔티티 변환
    public Post getPost(PostRequest postRequest) {
        return Post.builder()
                .title(postRequest.getTitle())
                .author(postRequest.getAuthor())
                .contents(postRequest.getContents())
                .isPublic(postRequest.isPublic())
                .build();
    }

    // 엔티티 리스트 → 응답 리스트 변환 (+ 좋아요 수, 상태 포함)
    public PostsResponse getPostsResponse(List<Post> posts,Map<Long, Integer> likeCountMap, Map<Long, Boolean> likedMap) {
        PostsResponse response = new PostsResponse();

        for(Post post : posts) {
            int likeCount = likeCountMap.getOrDefault(post.getId(),0);
            boolean liked = likedMap.getOrDefault(post.getId(), false);
            PostResponse postResponse = getPostResponse(post, likeCount, liked);
            response.addPost(postResponse);
        }

        return response;
    }
    // 엔티티 → 단건 응답 DTO 변환
    public PostResponse getPostResponse(Post post, int likeCount, boolean liked) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .author(post.getAuthor())
                .contents(post.getContents())
                .isPublic(post.isPublic())
                .likeCount(likeCount)
                .liked(liked)
                .build();
    }
    // 마이페이지용 - 단순 Post → PostResponse 변환 (제목 + 작성일)
    public PostResponse toPostSimpleResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .createdAt(post.getCreatedDate())
                .author(post.getAuthor())
                .contents(post.getContents())
                .isPublic(post.isPublic())
                .likeCount(0)
                .liked(false)
                .build();
    }

    public List<PostResponse> toPostSimpleResponseList(List<Post> posts) {
        return posts.stream()
                .map(this::toPostSimpleResponse)
                .collect(Collectors.toList());
    }



    
}

