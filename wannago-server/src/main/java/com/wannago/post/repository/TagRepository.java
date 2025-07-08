package com.wannago.post.repository;

import com.wannago.post.entity.Post;
import com.wannago.post.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(String name);

    @Query("select t.name from Tag t " +
            "where t.tagId in (select pt.tag.tagId from PostTag pt where pt.post.id = :postId)")
    List<String> getTagsByPost(@Param("postId") Long postId);
}
