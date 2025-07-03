package com.wannago.post.service;


import com.wannago.post.dto.PostRequest;
import com.wannago.post.dto.PostResponse;

public interface PostService {
    void insertPost(PostRequest postRequest);

    PostResponse getPostById(Long postId, Long memberId);

    PostResponse updatePost(Long postId, PostRequest postRequest, Long memberId);

    void deletePost(Long postId, Long memberId);
}
