package com.release.core.repository;

import com.release.core.domain.Post;
import com.release.core.domain.User;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post save(Post post);

    boolean delete(Long postId);

    Optional<Post> findById(Long postId);

    List<Post> findByWriterUserId(Long writerUserId, int page);

    int getAllPageFindByWriterUserId(Long writerUserId);

    List<Post> findByTripDays(Long tripDays, int page);

    int getAllPageFindByTripDays(Long tripDays);

    List<Post> getAllPosts(int page);

    int getAllPageGetAllPosts();

    List<Post> findByPostDate(String postData, int page);

    int getAllPageFindByPostDate(String postData);

    List<Post> findByQuery(String query, int page);

    int getAllPageFindByQuery(String query);


}
