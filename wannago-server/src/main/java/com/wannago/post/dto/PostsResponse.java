package com.wannago.post.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PostsResponse {
    private final List<PostSummaryInfo> posts;
    private final int totalPage;
    private final int currentPage;


    public PostsResponse(int totalPage, int currentPage) {
        posts = new ArrayList<>();
        this.totalPage = totalPage;
        this.currentPage = currentPage;
    }

    public void addPost(PostSummaryInfo post) {
        posts.add(post);
    }
}
