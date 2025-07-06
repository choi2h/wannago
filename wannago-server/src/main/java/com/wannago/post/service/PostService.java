package com.wannago.post.service;


import com.wannago.member.entity.Member;
import com.wannago.post.dto.PostRequest;
import com.wannago.post.dto.PostResponse;
import com.wannago.post.dto.PostsResponse;

public interface PostService {
    void insertPost(PostRequest postRequest);

    PostsResponse getPosts(int pageNo, String criteria);

    PostsResponse getPostsOrderByLikeCount(int pageNo);

    PostResponse getPostById(Long postId, Long memberId);

    PostResponse updatePost(Long postId, PostRequest postRequest, Long memberId);

    void deletePost(Long postId, String loginId);

    PostsResponse getPostsByAuthor(String author, int pageNo);
}
