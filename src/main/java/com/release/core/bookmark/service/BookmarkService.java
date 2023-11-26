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
    public Long doBookmark(Bookmark bookmark) {
        // 중복 등록 검증
        validateDuplicateBookmark(bookmark);
        bookmarkRepository.saveBookmark(bookmark);
        return bookmark.getBookmarkId();
    }

    private void validateDuplicateBookmark(Bookmark bookmark) {
        bookmarkRepository.findByPostId((bookmark.getUserId()), (bookmark.getPostId()))
            .ifPresent(b -> {
                throw new IllegalStateException("이미 북마크에 등록된 게시물입니다.");
            });
    }

    //삭제
    public void notBookmark(Long bookmarkId) {
        bookmarkRepository.deleteBookmark(bookmarkId);
    }
    
    //조회
    public List<Bookmark> loadBookmark(Long userId) {
        // userId: SESSION
        return bookmarkRepository.findAll(userId);
    }
    

    //테스트용
    public Optional<Bookmark> findTest(Long bookmarkId) {
        return bookmarkRepository.findById(bookmarkId);
    }

}
