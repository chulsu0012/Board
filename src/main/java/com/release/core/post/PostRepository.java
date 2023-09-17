package com.release.core.post;

import java.util.ArrayList;

public interface PostRepository {

    void save();
    void load();

    void write(Post post);

    ArrayList<Post> findByWriter(User writer);

    ArrayList<Post> searchPosts(int num);
}
