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

    List<Long> searchWithTagAndDays(List<Long> tagIdList, Long tripDays, int page);
    int getAllPageSearchWithTagAndDays(List<Long> tagIdList, Long tripDays);

    List<Long> searchWithTag(List<Long> tagIdList, int page);
    int getAllPageSearchWithTag(List<Long> tagIdList);

    List<Long> searchWithDays(Long tripDays, int page);
    int getAllPageSearchWithDays(Long tripDays);

    boolean delete(Long connectionId);
}
