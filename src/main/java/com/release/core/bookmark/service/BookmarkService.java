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
    public Long saveOne(Bookmark bookmark) {
        // 중복 등록 검증
        validateDuplicateBookmark(bookmark);
        bookmarkRepository.saveBookmark(bookmark);
        return bookmark.getBookmarkId();
    }

    private void validateDuplicateBookmark(Bookmark bookmark) {
        bookmarkRepository.findBookmarkByPostId((bookmark.getUserId()), (bookmark.getPostId()))
            .ifPresent(b -> {
                throw new IllegalStateException("이미 북마크에 등록된 게시물입니다.");
            });
    }

    //삭제
    public void deleteOne(Long bookmarkId) {
        bookmarkRepository.deleteBookmark(bookmarkId);
    }
    
    //전체 조회
    public List<Bookmark> findAll(Long userId) {
        // userId: SESSION
        return bookmarkRepository.findAllBookmarks(userId);
    }

    //단일 조회
    public Optional<Bookmark> findOne(Long bookmarkId) {
        return bookmarkRepository.findBookmarkByBookmarkId(bookmarkId);
    }

}
