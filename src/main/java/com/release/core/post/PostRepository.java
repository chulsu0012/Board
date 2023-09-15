package com.release.core.post;

import com.release.core.user.User;

import java.util.ArrayList;

public interface PostRepository {

    void save();
    void load();

    void write(Post post);

    ArrayList<Post> findByWriter(User writer);

    ArrayList<Post> searchPosts(int num);
}
