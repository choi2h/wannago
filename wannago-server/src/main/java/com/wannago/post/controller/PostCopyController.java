package com.wannago.post.controller;

import com.wannago.member.entity.Member;
import com.wannago.post.service.PostCopyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostCopyController {

    private final PostCopyService postCopyService;

    @PostMapping("/{postId}/copy")
    public ResponseEntity<Map<String, Object>> copyPost(
            @PathVariable Long postId,
            @AuthenticationPrincipal Member member
    ) {
        Long copiedPostId = postCopyService.copyPost(postId, member);
        return ResponseEntity.ok(Map.of(
                "message", "나의 여행에 복사되었습니다.",
                "copiedPostId", copiedPostId
        ));
    }
}

