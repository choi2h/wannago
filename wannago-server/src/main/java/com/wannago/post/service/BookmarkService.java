package com.wannago.post.service;

import java.util.List;
import java.util.Map;

public interface BookmarkService {

    boolean toggleBookmark(Long postId, Long memberId);
    boolean hasBookmarked(Long postId, Long memberId);
    Map<Long, Boolean> getBookmarkedMap(List<Long> postIds, Long memberId);
}
