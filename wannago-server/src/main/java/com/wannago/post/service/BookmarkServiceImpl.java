package com.wannago.post.service;

import com.wannago.common.exception.CustomException;
import com.wannago.member.entity.Member;
import com.wannago.member.repository.MemberRepository;
import com.wannago.post.entity.Bookmark;
import com.wannago.post.entity.Post;
import com.wannago.post.repository.BookmarkRepository;
import com.wannago.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wannago.common.exception.CustomErrorCode.MEMBER_NOT_EXIST;
import static com.wannago.common.exception.CustomErrorCode.POST_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    // 북마크 토글
    @Override
    public boolean toggleBookmark(Long postId, Long memberId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_EXIST));

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
    public boolean hasBookmarked(Long postId, Long memberId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_EXIST));
        return bookmarkRepository.existsByPostAndMember(post, member);
    }

    // 여러 게시글 북마크 여부 Map으로 반환
    @Override
    public Map<Long, Boolean> getBookmarkedMap(List<Long> postIds, Long memberId) {
        Map<Long, Boolean> result = new HashMap<>();

        if (memberId == null) {
            for (Long postId : postIds) result.put(postId, false);
            return result;
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_EXIST));

        List<Bookmark> bookmarks = bookmarkRepository.findByPostIdInAndMember(postIds, member);
        for (Bookmark b : bookmarks) {
            result.put(b.getPost().getId(), true);
        }

        // 나머지는 false 처리
        for (Long postId : postIds) {
            result.putIfAbsent(postId, false);
        }

        return result;
    }
}
