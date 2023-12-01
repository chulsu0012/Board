package com.release.core.bookmark.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.server.ResponseStatusException;

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

  // 북마크 등록
  // 해당 포스트 내에서 실행
  // postService 완성 이후에 구현
  @PostMapping("bookmark-save")
  public String saveBookmark(Long postId, @SessionAttribute("userid") Long userid) {
    Bookmark bookmark = new Bookmark();
    bookmark.setUserId(userid);
    bookmark.setPostId(postId);
    
    bookmarkService.saveOne(bookmark);

    return "redirect:/";
  }

  // 북마크 조회
  // 마이페이지에 접속했을 때 실행
  // postService 완성 이후에 구현
  @GetMapping("bookmark-load")
  public String loadBookmark(@SessionAttribute("userid") Long userid) {
    List<Bookmark> bookmarkList = bookmarkService.findAll(userid);

    List<Post> postList;
    for(int i=0;i<bookmarkList.size();i++){
    }

    /*
     * return JSON {
      * "posts": [
        *  Long PostId
        *  string PostTitle
        *  string postUserName
        *  string postDate
      * ]
     * }
     */
    return "user/userList";
  }

  // 북마크 삭제: Complete
  // 1. 해당 포스트 내에서 실행
  // 2. 마이페이지에 접속했을 때 실행
  @PostMapping("bookmark-delete")
  public String deleteBookmark(Long bookmarkId, @SessionAttribute("userid") Long userid) {
    Optional<Bookmark> bookmark = bookmarkService.findOne(bookmarkId);

    if(bookmark.isPresent()) {
      if(Objects.equals(bookmark.get().getUserId(), userid)) {
        bookmarkService.deleteOne(bookmarkId);
        return "redirect:/";
      }
      else {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "북마크를 삭제할 수 없습니다.");
      }
    }
    else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "북마크를 찾을 수 없습니다.");
    }
  }
}
