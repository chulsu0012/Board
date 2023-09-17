package com.release.core.bookmark;

public class Bookmark {
    private int bookmarkId;
    private int userId;
    private int postId;

    public Bookmark(int bookmarkId, int userId, int postId) {
        this.bookmarkId = bookmarkId;
        this.userId = userId;
        this.postId = postId;
    }

    public int getBookmarkId() {
        return this.bookmarkId;
    }

    public void setBookmarkId(int bookmarkId) {
        this.bookmarkId = bookmarkId;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return this.postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "Bookmark: " +
                "bookmakrId=" + bookmarkId +
                ", userId=" + userId +
                ", postId=" + postId;
    }
}
