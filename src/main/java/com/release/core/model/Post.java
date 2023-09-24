package com.release.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@ToString
public class Post {

    private final int postId;

    @Setter
    private String postTitle;

    @Setter
    private String postDate;

    @Setter
    private String postContent;

    @Setter
    private int postTripDays;

    private final User writer;

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
