package com.release.core.bookmark.repository;

import java.util.List;
import java.util.Optional;

import com.release.core.bookmark.domain.Bookmark;

public interface BookmarkRepository {
    Bookmark save(Bookmark bookmark);
    void delete(Long bookmarkId);
    Optional<Bookmark> findByBookmarkId(Long bookmarkId);
    Optional<Bookmark> findByPostId(Long postId);
    List<Bookmark> findAll(Long userId);
}
