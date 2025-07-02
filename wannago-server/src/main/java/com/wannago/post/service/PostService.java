package com.wannago.post.service;


import com.wannago.post.dto.PostRequest;
import com.wannago.post.dto.PostResponse;

public interface PostService {
    void insertPost(PostRequest postRequest);

    PostResponse getPostById(Long postId, Long memberId);
}
