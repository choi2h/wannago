package com.wannago.post.service.mapper;

import com.wannago.post.dto.PostRequest;
import com.wannago.post.dto.PostResponse;
import com.wannago.post.dto.PostsResponse;
import com.wannago.post.entity.Post;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostMapper {
    public Post getPost(PostRequest postRequest) {
        return Post.builder()
                .title(postRequest.getTitle())
                .author(postRequest.getAuthor())
                .contents(postRequest.getContents())
                .isPublic(postRequest.isPublic())
                .build();
    }


    public PostsResponse getPostsResponse(List<Post> posts) {
        PostsResponse response = new PostsResponse();

        for(Post post : posts) {
            PostResponse postResponse = getPostResponse(post);
            response.addPost(postResponse);
        }

        return response;
    }

    public PostResponse getPostResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .author(post.getAuthor())
                .contents(post.getContents())
                .isPublic(post.isPublic())
                .build();
    }
}
