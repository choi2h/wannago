package com.wannago.post.service;

import com.wannago.common.exception.CustomException;
import com.wannago.member.entity.Member;
import com.wannago.member.repository.MemberRepository;
import com.wannago.post.dto.PostLikeCount;
import com.wannago.post.dto.PostsResponse;
import com.wannago.post.entity.Post;
import com.wannago.post.entity.PostLike;
import com.wannago.post.repository.PostLikeRepository;
import com.wannago.post.repository.PostRepository;
import com.wannago.post.service.mapper.PostMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.wannago.common.exception.CustomErrorCode.MEMBER_NOT_EXIST;
import static com.wannago.common.exception.CustomErrorCode.POST_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class PostLikeServiceImpl implements PostLikeService {

    //생성자 주입(@RequiredArgsConstructor)
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostMapper postMapper;

    // 좋아요 토글 기능: 게시글에 대한 사용자의 좋아요 상태를 반대로 전환
    @Override
    @Transactional
    public boolean toggleLike(Long postId, Long memberId) {
        // 게시글 유효성 검증
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        // 사용자 유효성 검증
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_EXIST));
        // Optinal로 좋아요 존재 확인
        Optional<PostLike> like = postLikeRepository.findByPostAndMember(post, member);

        // 좋아요 누른 상태라면 => 좋아요 해제
        if (like.isPresent()) {
            postLikeRepository.delete(like.get());
            return false;
        }
        // 안누른 상태라면 => 좋아요 등록
            postLikeRepository.save(new PostLike(null, post, member));
            return true;

    }

    // 단일 게시글의 총 좋아요 수 조회
    @Override
    public int getLikeCount(Long postId) {
        // 게시글 유효성 검증
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        return postLikeRepository.countByPost(post);
    }

    // 사용자가 해당 게시글에 좋아요 눌렀는지 빠르게 확인
    @Override
    public boolean hasLiked(Long postId, Long memberId) {
        // 게시글 유효성 검증
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        // 사용자 유효성 검증
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_EXIST));
        return postLikeRepository.existsByPostAndMember(post, member);
    }

    // 전체 게시글에 대한 좋아요 정보 함께 응답
    @Override
    public PostsResponse getAllPostsWithLikeInfo(Long memberId) {
        // 전체 게시글 목록 가져오기
        List<Post> posts = postRepository.findAll();
        // 그 게시글들의 ID만 추출해서 리스트 만듦
        List<Long> postIds = posts.stream().map(Post::getId).toList();

        // 전체 게시글의 좋아요 수 추출한 리스트 생성
        List<PostLikeCount> counts = postLikeRepository.countLikesByPostIds(postIds);
        // 전체 게시글 ID와 그에 해당하는 좋아요 수를 매핑할 Map 생성
        Map<Long, Integer> likeCountMap = new HashMap<>();
        // 맵에 게시글 id와 좋아요 수 값을 넣음
        for (PostLikeCount plc : counts) {
            likeCountMap.put(plc.getPostId(), plc.getLikeCount().intValue());
        }

        // 현재 로그인한 사용자가 전체 게시글에 대하여 좋아요 눌렀는지 여부를 매핑한 map
        Map<Long, Boolean> likedMap = new HashMap<>();
        //로그인한 상태
        if (memberId != null) {
            // 사용자 유효성 검증
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new CustomException(MEMBER_NOT_EXIST));
            // 해당 유저가 좋아요 누른 게시물의 좋아요 객체들의 리스트
            List<PostLike> likes = postLikeRepository.findByPostIdInAndMember(postIds, member);
            // likedMap에 사용자 ID와 좋아요 등록 boolean 값을 넣음
            for (PostLike like : likes) {
                likedMap.put(like.getPost().getId(), true);
            }
        }
        // 로그인 안 한 상태
        else {
            for (Long id : postIds) {
                likedMap.put(id, false); // 로그인 안했으니 아무것도 누른적 없다고 설정
            }
        }
        // 응답 DTO 리스트로 전달
        return postMapper.getPostsResponse(posts, likeCountMap, likedMap);
    }
}
