package com.release.core.post;

import com.release.core.user.model.User;
import lombok.Getter;
import lombok.Setter;

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
    public String postContent;

    @Getter
    @Setter
    public int postTripDays;

    @Getter
    public final User writer;

    public Post(int postId, User writer) {
        this.postId = postId;
        this.writer = writer;

        edit("", "", "", 0);
    }

    public Post(int postId, User writer, String postTitle, String postDate, String postContent, int postTripDays) {
        this.postId = postId;
        this.writer = writer;

        edit(postTitle, postDate, postContent, postTripDays);
    }

    public void edit(String postTitle, String postDate, String postContent, int postTripDays) {
        this.postTitle = postTitle;
        this.postDate = postDate;
        this.postContent = postContent;
        this.postTripDays = postTripDays;
    }

    public boolean equals(Post other) {
        return this.postId==other.postId;
    }




}
