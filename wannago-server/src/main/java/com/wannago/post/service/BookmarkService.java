package com.wannago.post.service;

import com.wannago.member.entity.Member;

import java.util.List;
import java.util.Map;

public interface BookmarkService {

    boolean hasBookmarked(Long postId, Member member);
    void addBookmark(Long postId, Member member);
    Map<Long, Boolean> getBookmarkedMap(List<Long> postIds, Member member);
}
