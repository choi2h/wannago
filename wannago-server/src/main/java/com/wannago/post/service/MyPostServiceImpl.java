package com.wannago.post.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.wannago.common.exception.CustomErrorCode.MEMBER_NOT_FOUND;
import com.wannago.common.exception.CustomException;
import com.wannago.member.entity.Member;
import com.wannago.member.repository.MemberRepository;
import com.wannago.post.dto.MyPostResponse;
import com.wannago.post.entity.Post;
import com.wannago.post.repository.PostRepository;



@Service
@RequiredArgsConstructor
public class MyPostServiceImpl implements MyPostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostLikeService postLikeService; // 👍 좋아요 서비스

    @Override
    public List<MyPostResponse> getMyPosts(String loginId) {
        // 로그인 아이디로 회원 조회
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        // 사용자가 작성한 게시글 목록 조회 (최신순 정렬)
        List<Post> posts = postRepository.findByAuthorOrderByCreatedDateDesc(loginId);


        // 게시글 ID만 추출
        List<Long> postIds = posts.stream()
                .map(Post::getId)
                .toList();

        // 좋아요 수 및 좋아요 여부 Map 조회
        Map<Long, Integer> likeCountMap = postLikeService.getLikeCountMap(postIds);
        Map<Long, Boolean> likedMap = postLikeService.getLikedMap(postIds, member);

        // 응답 DTO 매핑
        return posts.stream()
                .map(post -> MyPostResponse.builder()
                        .postId(post.getId())
                        .title(post.getTitle())
                        .hashtags(
                                post.getTags().stream()
                                        .map(pt -> pt.getTag().getName())
                                        .collect(Collectors.toList())
                        )
                        .isPublic(post.isPublic())
                        .likeCount(likeCountMap.getOrDefault(post.getId(), 0))
                        .liked(likedMap.getOrDefault(post.getId(), false))
                        .createdDate(post.getCreatedDate())
                        .build()
                )
                .collect(Collectors.toList());
    }
}