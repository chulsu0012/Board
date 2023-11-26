package com.release.core.repository;

import com.release.core.domain.Post;
import com.release.core.domain.PostTagsConnection;

import java.util.List;
import java.util.Optional;

public interface PostTagsConnectionRepository {

    PostTagsConnection save(PostTagsConnection postTagConnection);

    Optional<PostTagsConnection> findById(Long connectionId);

    List<PostTagsConnection> findByPostId(Long postId);

    List<PostTagsConnection> findByTagId(Long tagId);

    Optional<PostTagsConnection> findByPostIdAndTagId(Long postId, Long tagId);

    List<Long> search(List<Long> tagIdList, Long page, Long tripDays);

    boolean delete(Long connectionId);
}
