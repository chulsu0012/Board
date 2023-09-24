package com.release.core.repository;

import com.release.core.model.Post;
import com.release.core.model.User;

import java.util.ArrayList;

public class SQLPostRepository implements PostRepository {

    private ArrayList<Post> posts = new ArrayList<>();

    public SQLPostRepository() {
        this.load();
    }

    @Override
    public void load() {
        // TODO : SQL Load
    }

    @Override
    public void save() {
        // TODO : SQL Save
    }

    @Override
    public void write(Post post) {
        this.posts.add(post);
        save();
    }

    @Override
    public ArrayList<Post> findByWriter(User target) {
        ArrayList<Post> postsByWriter = new ArrayList<>();
        for(Post post : this.posts) {
            if(post.getWriter().equals(target)) {
                postsByWriter.add(post);
            }
        }
        return postsByWriter;
    }

    @Override
    public ArrayList<Post> searchPosts(int num) {
        ArrayList<Post> searchedPosts = new ArrayList<>();

        // TODO : 날짜 기준 Sorting

        return searchedPosts;
    }





}
