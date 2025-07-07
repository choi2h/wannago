package com.wannago.post.service;

import java.util.List;

import com.wannago.member.entity.Member;
import com.wannago.post.dto.MyPostResponse;

public interface MyPostService {
    List<MyPostResponse> getMyPosts(Member member);
    
}
