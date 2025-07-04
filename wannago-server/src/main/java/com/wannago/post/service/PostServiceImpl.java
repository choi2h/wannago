package com.wannago.post.service;

import com.wannago.common.exception.CustomErrorCode;
import com.wannago.common.exception.CustomException;
import com.wannago.post.dto.PostRequest;
import com.wannago.post.dto.PostResponse;
import com.wannago.post.dto.PostStatusInfo;
import com.wannago.post.entity.Post;
import com.wannago.post.entity.Tag;
import com.wannago.post.repository.BookmarkRepository;
import com.wannago.post.repository.PostLikeRepository;
import com.wannago.post.repository.PostRepository;
import com.wannago.post.repository.TagRepository;
import com.wannago.post.service.mapper.PostMapper;
import com.wannago.qna.entity.Ask;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service @Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostLikeRepository postLikeRepository;
    private final BookmarkRepository bookmarkRepository;
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final PostMapper postMapper;

    // 게시글 등록
    @Override
    public void insertPost(PostRequest postRequest) {
        Post post = postMapper.getPost(postRequest, true);
        if(postRequest.getTags() != null && !postRequest.getTags().isEmpty()) {
            setTags(post, postRequest.getTags());
        }

        postRepository.save(post);
    }

    @Override
    public PostResponse getPostById(Long postId, Long memberId) {
        Post post = postRepository
                    .findByIdWithSchedules(postId)
                    .orElseThrow(() -> new CustomException(CustomErrorCode.POST_NOT_FOUND));
        List<String> tags = tagRepository.getTagsByPost(postId);
        PostStatusInfo statusInfo = getPostStatusInfo(postId, memberId);
        return postMapper.getPostResponse(post, tags, statusInfo);
    }

    @Override
    public PostResponse updatePost(Long postId, PostRequest postRequest, Long memberId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.POST_NOT_FOUND));


        // request값으로 post수정
        post.updateTitle(postRequest.getTitle());
        post.updateContents(postRequest.getContents());

        // 기존의 연관관계 배열값들 다 삭제
        post.clearSchedules();
        post.clearTags();

        // 새로운 데이터로 넣어줌
        postMapper.addSchedules(post, postRequest.getSchedules());
        setTags(post, postRequest.getTags());

        List<String> tags = post.getTags().stream()
                .map(postTag -> postTag.getTag().getName()).toList();
        PostStatusInfo statusInfo = getPostStatusInfo(postId, memberId);
        return postMapper.getPostResponse(post, tags, statusInfo);
    }

    private PostStatusInfo getPostStatusInfo(Long postId, Long memberId) {
        int likeCount = postLikeRepository.countByPost_Id(postId);

        // TODO 추후 로그인 정보 개발 완료되면 수정
        if(memberId == null) return new PostStatusInfo(likeCount, false, false);
        boolean isLiked = postLikeRepository.existsByPost_IdAndMember_Id(postId, memberId);
        boolean isBookmarked = bookmarkRepository.existsByPost_IdAndMember_Id(postId, memberId);
        return new PostStatusInfo(likeCount, isLiked, isBookmarked);
    }
@Transactional(readOnly = true)
public List<PostResponse> getMyPosts(String loginId) {
    List<Post> myPosts = postRepository.findByAuthor(loginId);
    return postMapper.toPostSimpleResponseList(myPosts);
}


    private void setTags(Post post, List<String> inputTags) {
        for(String inputTag : inputTags) {
            String name = inputTag.trim();
            Optional<Tag> tagOptional = tagRepository.findByName(name);
            Tag tag = tagOptional.orElseGet(() -> tagRepository.save(new Tag(name)));
            post.addTag(tag);
        }
    }
}


