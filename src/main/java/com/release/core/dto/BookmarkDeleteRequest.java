package com.release.core.dto;

import lombok.Data;

@Data
public class BookmarkDeleteRequest {
  private Long bookmarkId;

  public BookmarkDeleteRequest(Long bookmarkId, Long userId, Long postId) {
      this.bookmarkId = bookmarkId;
  }
}
