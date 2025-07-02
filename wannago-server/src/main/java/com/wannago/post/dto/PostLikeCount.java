package com.wannago.post.dto;

// dto 프로젝션: 필요한 필드만 담은 인터페이스나 클래스를 만들어서, 쿼리 결과를 거기에 바로 매핑 (집계용)
public interface PostLikeCount {
    Long getPostId();
    Long getLikeCount();
}