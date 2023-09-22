package com.release.core.post.model;

import com.release.core.post.model.tag.Tag;
import com.release.core.user.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Post {

    @Getter
    private final int postId;

    @Getter
    @Setter
    private String postTitle;

    @Getter
    @Setter
    private String postDate;

    @Getter
    @Setter
    private String postContent;

    @Getter
    @Setter
    private int postTripDays;

    @Getter
    private final User writer;

    @Getter
    private ArrayList<Tag> tags;

    public Post(int postId, User writer) {
        this.postId = postId;
        this.writer = writer;

        edit("", "", "", 0, new ArrayList<Tag>());
    }

    public Post(int postId, User writer, String postTitle, String postDate, String postContent, int postTripDays, ArrayList<Tag> tags) {
        this.postId = postId;
        this.writer = writer;

        edit(postTitle, postDate, postContent, postTripDays, tags);
    }

    public void edit(String postTitle, String postDate, String postContent, int postTripDays, ArrayList<Tag> tags) {
        this.postTitle = postTitle;
        this.postDate = postDate;
        this.postContent = postContent;
        this.postTripDays = postTripDays;
        this.tags = tags;
    }

    public boolean equals(Post other) {
        return this.postId==other.postId;
    }




}
