package com.wannago.post.service;

import com.wannago.common.exception.CustomErrorCode;
import com.wannago.common.exception.CustomException;
import com.wannago.member.entity.Member;
import com.wannago.member.repository.MemberRepository;
import com.wannago.post.dto.PostRequest;
import com.wannago.post.dto.PostResponse;
import com.wannago.post.dto.PostStatusInfo;
import com.wannago.post.dto.PostsResponse;
import com.wannago.post.entity.Bookmark;
import com.wannago.post.entity.Post;
import com.wannago.post.entity.PostLike;
import com.wannago.post.repository.PostRepository;
import com.wannago.post.service.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.wannago.common.exception.CustomErrorCode.MEMBER_NOT_EXIST;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
// 기존 코드
//    private final PostRepository postRepository;
//    private final PostMapper postMapper;

    private final PostRepository postRepository;
    private final PostLikeService postLikeService;
    
    private final BookmarkService bookmarkService;
    private final PostMapper postMapper;
    // 기존 코드
//    public void insertPost(PostRequest postRequest) {
//        Post post = postMapper.getPost(postRequest);
//        postRepository.save(post);
//    }
//
//    public PostsResponse getAllPosts() {
//        List<Post> posts = postRepository.findAll();
//        return postMapper.getPostsResponse(posts);
//    }

//    public PostResponse getPostById(Long id) {
//        Optional<Post> post = postRepository.findById(id);
//        if(post.isEmpty()) {
//            throw new CustomException(CustomErrorCode.POST_NOT_FOUND);
//        }
//
//        return postMapper.getPostResponse(post.get());
//    }

        // 게시글 등록
        @Override
        public void insertPost(PostRequest postRequest) {
            Post post = postMapper.getPost(postRequest);
            postRepository.save(post);
        }

        // 단일 게시글 조회
        @Override
        public PostResponse getPostById(Long id, Long memberId) {
            Post post = postRepository.findById(id)
                    .orElseThrow(() -> new CustomException(CustomErrorCode.POST_NOT_FOUND));

            int likeCount = postLikeService.getLikeCount(id);
            boolean liked = memberId != null && postLikeService.hasLiked(id, memberId);
            boolean bookmarked = memberId != null && bookmarkService.hasBookmarked(id, memberId);

            PostStatusInfo status = new PostStatusInfo(likeCount, liked, bookmarked);
            return postMapper.getPostResponse(post, status);
        }

    // 전체 게시글 조회 (좋아요/북마크 포함)
    @Override
    public PostsResponse getAllPostsWithStatusInfo(Long memberId) {
        List<Post> posts = postRepository.findAll();
        List<Long> postIds = posts.stream().map(Post::getId).toList();

        Map<Long, Integer> likeCountMap = postLikeService.getLikeCountMap(postIds);
        Map<Long, Boolean> likedMap = postLikeService.getLikedMap(postIds, memberId);
        Map<Long, Boolean> bookmarkedMap = bookmarkService.getBookmarkedMap(postIds, memberId);

        Map<Long, PostStatusInfo> statusMap = new HashMap<>();
        for (Long postId : postIds) {
            statusMap.put(postId, new PostStatusInfo(
                    likeCountMap.getOrDefault(postId, 0),
                    likedMap.getOrDefault(postId, false),
                    bookmarkedMap.getOrDefault(postId, false)
            ));
        }

        return postMapper.getPostsResponse(posts, statusMap);
    }





}


