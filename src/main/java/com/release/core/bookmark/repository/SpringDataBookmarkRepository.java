package com.release.core.bookmark.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.release.core.bookmark.domain.Bookmark;

public interface SpringDataBookmarkRepository extends JpaRepository<Bookmark, Long>, BookmarkRepository {
    @Override
    Optional<Bookmark> findByBookmarkId(Long bookmarkId);
}
