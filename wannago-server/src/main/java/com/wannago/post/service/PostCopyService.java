package com.wannago.post.service;

import com.wannago.common.exception.CustomException;
import com.wannago.member.entity.Member;
import com.wannago.post.entity.Post;
import com.wannago.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.wannago.common.exception.CustomErrorCode.POST_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PostCopyService {

    private final PostRepository postRepository;

    @Transactional
    public Long copyPost(Long originalPostId, Member member) {
        Post original = postRepository.findById(originalPostId)
                .filter(Post::isPublic) // 공개된 게시글만 복사 가능
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

        Post copied = Post.builder()
                .title("[복사본] " + original.getTitle())
                .contents(original.getContents())
                .author(member.getLoginId())
                .isPublic(false) // 복사본은 무조건 비공개
                .build();

        postRepository.save(copied);
        return copied.getId();
    }
}

