package com.wannago.post.service;

import com.wannago.common.exception.CustomErrorCode;
import com.wannago.common.exception.CustomException;
import com.wannago.post.dto.PostRequest;
import com.wannago.post.dto.PostResponse;
import com.wannago.post.dto.PostsResponse;
import com.wannago.post.entity.Post;
import com.wannago.post.repository.PostRepository;
import com.wannago.post.service.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public void insertPost(PostRequest postRequest) {
        Post post = postMapper.getPost(postRequest);
        postRepository.save(post);
    }

    public PostsResponse getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return postMapper.getPostsResponse(posts);
    }

    public PostResponse getPostById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if(post.isEmpty()) {
            throw new CustomException(CustomErrorCode.POST_NOT_FOUND);
        }

        return postMapper.getPostResponse(post.get());
    }

}
