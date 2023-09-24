package com.release.core.repository;

import com.release.core.model.Post;
import com.release.core.model.User;

import java.util.ArrayList;

public interface PostRepository {

    void save();
    void load();

    void write(Post post);

    ArrayList<Post> findByWriter(User writer);

    ArrayList<Post> searchPosts(int num);
}
