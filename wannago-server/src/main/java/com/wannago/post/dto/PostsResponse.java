package com.wannago.post.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PostsResponse {
    private final List<PostResponse> posts;

    public PostsResponse() {
        posts = new ArrayList<>();
    }

    public void addPost(PostResponse post) {
        posts.add(post);
    }

}
