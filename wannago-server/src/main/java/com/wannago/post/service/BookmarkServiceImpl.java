package com.wannago.post.service;

import com.wannago.common.exception.CustomException;
import com.wannago.member.entity.Member;
import com.wannago.post.entity.Bookmark;
import com.wannago.post.entity.Post;
import com.wannago.post.repository.BookmarkRepository;
import com.wannago.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wannago.common.exception.CustomErrorCode.POST_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final PostRepository postRepository;

    // 북마크 등록
    @Override
    @Transactional // 트랜잭션 추가 (저장 작업이므로)
    public void addBookmark(Long postId, Member member) {
        // 게시글 존재 여부 확인
        Post post = getPostOrThrow(postId);

        // 이미 북마크 되어 있는지 확인
        if (bookmarkRepository.existsByPostAndMember(post, member)) {
            // 이미 북마크 되어 있다면, 예외 발생 또는 성공으로 간주 (여기서는 예외 발생으로 처리)
            throw new CustomException(CustomErrorCode.ALREADY_BOOKMARKED);
        }
        // 북마크 저장
        bookmarkRepository.save(new Bookmark(null, post, member));
    }

    // 게시글 단건 북마크 여부 확인
    @Override
    public boolean hasBookmarked(Long postId, Member member) {
        Post post = getPostOrThrow(postId);
        return bookmarkRepository.existsByPostAndMember(post, member);
    }

    // 여러 게시글 북마크 여부 Map으로 반환
    @Override
    public Map<Long, Boolean> getBookmarkedMap(List<Long> postIds, Member member) {
        Map<Long, Boolean> result = new HashMap<>();

        if (member == null) {
            for (Long postId : postIds) result.put(postId, false);
            return result;
        }

        List<Bookmark> bookmarks = bookmarkRepository.findByPostIdInAndMember(postIds, member);
        for (Bookmark b : bookmarks) {
            result.put(b.getPost().getId(), true);
        }

        for (Long postId : postIds) {
            result.putIfAbsent(postId, false);
        }

        return result;
    }

    // 게시글 조회 헬퍼
    private Post getPostOrThrow(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
    }
}
