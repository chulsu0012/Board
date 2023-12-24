package com.release.core.dto;

import lombok.Data;

@Data
public class BookmarkFormDTO {
  private Long userId;
  private Long postId;

  public BookmarkFormDTO(Long bookmarkId, Long userId, Long postId) {
      this.userId = userId;
      this.postId = postId;
  }
}
