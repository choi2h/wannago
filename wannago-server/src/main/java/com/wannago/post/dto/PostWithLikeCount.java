package com.wannago.post.dto;

import com.wannago.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostWithLikeCount {
    private final Post post;
    private final long likeCount;
}
