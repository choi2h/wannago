package com.wannago.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostStatusInfo {
    private int likeCount;
    private boolean liked;
    private boolean bookmarked;
}
