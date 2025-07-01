package com.wannago.post.service.mapper;

import com.wannago.post.dto.PostRequest;
import com.wannago.post.dto.PostResponse;
import com.wannago.post.dto.PostStatusInfo;
import com.wannago.post.dto.PostsResponse;
import com.wannago.post.entity.Post;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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

    // 엔티티 → 단건 응답 DTO 변환
    public PostResponse getPostResponse(Post post, PostStatusInfo statusInfo) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .author(post.getAuthor())
                .contents(post.getContents())
                .isPublic(post.isPublic())
                .likeCount(statusInfo.getLikeCount())
                .liked(statusInfo.isLiked())
                .bookmarked(statusInfo.isBookmarked())
                .build();
    }

    // 엔티티 리스트 → 응답 리스트 변환
    public PostsResponse getPostsResponse(List<Post> posts, Map<Long, PostStatusInfo> statusMap) {
        PostsResponse response = new PostsResponse();

        for (Post post : posts) {
            PostStatusInfo status = statusMap.getOrDefault(post.getId(),
                    new PostStatusInfo(0, false, false));
            PostResponse postResponse = getPostResponse(post, status);
            response.addPost(postResponse);
        }

        return response;
    }


}
