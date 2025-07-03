package com.wannago.post.service;

import com.wannago.member.entity.Member;
import com.wannago.post.entity.Post;

import java.util.List;
import java.util.Map;

public interface BookmarkService {

    boolean toggleBookmark(Long postId, Member member);
    boolean hasBookmarked(Long postId, Member member);
    Map<Long, Boolean> getBookmarkedMap(List<Long> postIds, Member member);
    List<Post> getBookmarks(Member member);
    void deleteBookmark(Post post, Member member);
}
