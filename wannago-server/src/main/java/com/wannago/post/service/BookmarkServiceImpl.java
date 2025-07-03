package com.wannago.post.service;

import com.wannago.common.exception.CustomException;
import com.wannago.member.entity.Member;
import com.wannago.post.entity.Bookmark;
import com.wannago.post.entity.Post;
import com.wannago.post.repository.BookmarkRepository;
import com.wannago.post.repository.PostRepository;
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

    // 북마크 토글
    @Override
    public boolean toggleBookmark(Long postId, Member member) {
        Post post = getPostOrThrow(postId);

        return bookmarkRepository.findByPostAndMember(post, member)
                .map(bookmark -> {
                    bookmarkRepository.delete(bookmark);
                    return false;
                })
                .orElseGet(() -> {
                    bookmarkRepository.save(new Bookmark(null, post, member));
                    return true;
                });
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
    public void deleteBookmark(Post post, Member member) {
    bookmarkRepository.findByPostAndMember(post, member)
            .ifPresent(bookmarkRepository::delete);
}
    public List<Post> getBookmarks(Member member) {
    List<Bookmark> bookmarks = bookmarkRepository.findByMember(member);
    return bookmarks.stream()
            .map(Bookmark::getPost)
            .toList();
}




}
