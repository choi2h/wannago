package com.wannago.post.service;

import com.wannago.member.entity.Member;
import com.wannago.post.dto.PostRequest;
import com.wannago.post.dto.PostResponse;
import com.wannago.post.dto.PostsResponse;

import java.util.List;

public interface PostService {
    void insertPost(PostRequest postRequest);
    PostsResponse getAllPostsWithStatusInfo(Member member); 
    PostResponse getPostById(Long id, Member member);
    
    PostResponse getPostById(Long id);
    List<PostResponse> getMyPosts(String loginId);
    List<PostResponse> getMyQnaList(String loginId);
    
}
