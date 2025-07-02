package com.wannago.post.service;


import com.wannago.post.dto.PostRequest;
import com.wannago.post.dto.PostResponse;
import com.wannago.post.dto.PostsResponse;

public interface PostService {
    void insertPost(PostRequest postRequest);
    // PostsResponse getAllPosts();
    PostResponse getPostById(Long id, Long memberId);
    PostsResponse getAllPostsWithStatusInfo(Long memberId);
}
