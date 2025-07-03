package com.wannago.post.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PostsResponse {
    private final List<PostSummaryInfo> posts;

    public PostsResponse() {
        posts = new ArrayList<>();
    }

    public void addPost(PostSummaryInfo post) {
        posts.add(post);
    }
}
