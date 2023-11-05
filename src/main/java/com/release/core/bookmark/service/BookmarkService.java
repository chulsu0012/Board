package com.release.core.bookmark.service;

import java.util.List;
import java.util.Optional;

import com.release.core.bookmark.domain.Bookmark;
import com.release.core.bookmark.repository.BookmarkRepository;

import jakarta.transaction.Transactional;

@Transactional
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    public BookmarkService(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    //등록
    public Long save(Bookmark bookmark) {
        // 중복 등록 검증
        validateDuplicateBookmark(bookmark);
        bookmarkRepository.save(bookmark);
        return bookmark.getBookmarkId();
    }

    private void validateDuplicateBookmark(Bookmark bookmark) {
        bookmarkRepository.findByPostId(bookmark.getPostId())
            .ifPresent(b -> {
                throw new IllegalStateException("이미 북마크에 등록된 게시물입니다.");
            });
    }

    //조회
    public List<Bookmark> load(Long userId) {
        return bookmarkRepository.findAll(userId);
    }

    //검색
    public Optional<Bookmark> find(Long postId) {
        return bookmarkRepository.findByPostId(postId);
    }

    //삭제
    public void delete(Long bookmarkId) {
        bookmarkRepository.delete(bookmarkId);
    }

}
