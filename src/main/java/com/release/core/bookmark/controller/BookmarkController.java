package com.release.core.bookmark.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.release.core.bookmark.domain.Bookmark;
import com.release.core.bookmark.service.BookmarkService;
import com.release.core.domain.Post;

@Controller
public class BookmarkController {
  
  private final BookmarkService bookmarkService;

  @Autowired
  public BookmarkController(BookmarkService bookmarkService) {
    this.bookmarkService = bookmarkService;
  }

  // userId: SESSION
  Long userId;

  // 북마크 등록
  // 해당 포스트 내에서 실행
  @PostMapping("/bookmarksave")
  public String save(Long postId) {
    Bookmark bookmark = new Bookmark();
    bookmark.setPostId(postId);
    bookmark.setUserId(userId);
    
    bookmarkService.doBookmark(bookmark);

    return "redirect:/";
  }

  // 북마크 조회
  @GetMapping("/bookmarkload")
  public String load(Model model) {
    List<Bookmark> bookmarks = bookmarkService.loadBookmark(userId);
    model.addAttribute("bookmarks", bookmarks);
    return "bookmarks/bookmarkList";
  }

  // 북마크 삭제
  // 1. 해당 포스트 내에서 실행
  // 2. 북마크 리스트 내에서 실행
  @PostMapping("/bookmarkdelete")
  public String delete(Long bookmarkId) {
    Long returnBookmarkId = bookmarkService.notBookmark(bookmarkId);
    return "redirect:/";
  }

}
