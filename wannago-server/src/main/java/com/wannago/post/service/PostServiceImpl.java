package com.wannago.post.service;

import com.wannago.post.dto.PostRequest;
import com.wannago.post.entity.Post;
import com.wannago.post.entity.Tag;
import com.wannago.post.repository.PostRepository;
import com.wannago.post.repository.TagRepository;
import com.wannago.post.service.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final PostMapper postMapper;

        // 게시글 등록
        @Override
        public void insertPost(PostRequest postRequest) {
            Post post = postMapper.getPost(postRequest, true);
            setTags(post, postRequest.getTags());
            postRepository.save(post);
        }

        private void setTags(Post post, List<String> inputTags) {
            for(String inputTag : inputTags) {
                System.out.println(inputTag);
                String name = inputTag.trim();
                Optional<Tag> tagOptional = tagRepository.findByName(name);
                Tag tag = tagOptional.orElseGet(() -> tagRepository.save(new Tag(name)));
                post.addTag(tag);
            }
        }
}


