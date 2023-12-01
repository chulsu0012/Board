package com.release.core.bookmark.repository;

import java.util.List;
import java.util.Optional;

import com.release.core.bookmark.domain.Bookmark;

public interface BookmarkRepository {
    Bookmark saveBookmark(Bookmark bookmark);
    void deleteBookmark(Long bookmarkId);
    Optional<Bookmark> findBookmarkByBookmarkId(Long bookmarkId);
    Optional<Bookmark> findBookmarkByPostId(Long userId, Long postId);
    List<Bookmark> findAllBookmarks(Long userId);
}
