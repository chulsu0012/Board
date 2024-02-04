package com.release.core.dto;

import lombok.Data;

@Data
public class BookmarkSaveRequest {
  private Long postId;

  public BookmarkSaveRequest(Long bookmarkId, Long userId, Long postId) {
      this.postId = postId;
  }
}
