package com.release.core.service;

import java.util.List;
import java.util.Optional;

import com.release.core.domain.Bookmark;
import com.release.core.repository.BookmarkRepository;

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

    //단일 삭제
    public void deleteOne(Long bookmarkId) {
        bookmarkRepository.deleteBookmark(bookmarkId);
    }

    //특정 게시물의 북마크 전체 삭제
    public void deleteAllRelatedOne(Long postId) {
        bookmarkRepository.deleteAllRelatedBookmark(postId);
    }
    
    //전체 조회
    public List<Bookmark> findAll(Long userId, Long pageNumber) {
        return bookmarkRepository.findAllBookmarks(userId, pageNumber);
    }

    //단일 조회
    public Optional<Bookmark> findOne(Long bookmarkId) {
        return bookmarkRepository.findBookmarkByBookmarkId(bookmarkId);
    }

    //전체 북마크 개수
    public int findTotalPages(Long userId) {
        return bookmarkRepository.findBookmarksNumber(userId);
    }
}
