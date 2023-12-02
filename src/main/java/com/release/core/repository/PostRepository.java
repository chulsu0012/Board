package com.release.core.repository;

import com.release.core.domain.Post;
import com.release.core.domain.User;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post save(Post post);


    Optional<Post> findById(Long postId);

    List<Post> findByWriterUserId(Long writerUserId, int start);

    List<Post> findByTripDays(Long tripDays, int start);

    List<Post> getAllPosts(int start);

    List<Post> findByPostDate(String postData, int start);
    boolean delete(Long postId);
}
