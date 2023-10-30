package com.release.core.repository;

import com.release.core.domain.Post;
import com.release.core.domain.User;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post save(Post post);

    Optional<Post> findById(Long postId);

    List<Post> findByWriterUserId(Long writerUserId);

    //List<Post> searchPosts(int num);
}
