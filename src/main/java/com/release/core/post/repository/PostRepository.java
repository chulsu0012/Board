package com.release.core.post.repository;

import com.release.core.post.model.Post;

import java.util.ArrayList;

public interface PostRepository {

    void save();
    void load();

    void write(Post post);

    ArrayList<Post> findByWriter(User writer);

    ArrayList<Post> searchPosts(int num);
}
