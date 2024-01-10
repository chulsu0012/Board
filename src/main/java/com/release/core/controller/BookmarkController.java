package com.release.core.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.server.ResponseStatusException;

import com.release.core.domain.Bookmark;
import com.release.core.domain.Post;
import com.release.core.dto.BookmarkFormDTO;
import com.release.core.service.BookmarkService;
import com.release.core.service.PostService;

@Controller
public class BookmarkController {

  private final BookmarkService bookmarkService;
  private final PostService postService;

  @Autowired
  public BookmarkController(BookmarkService bookmarkService, PostService postService) {
    this.bookmarkService = bookmarkService;
    this.postService = postService;
  }

  // 북마크 등록: Complete
  // 해당 포스트 내에서 실행
  @PostMapping("bookmark-save")
  @ResponseBody
  public ResponseEntity<String> saveBookmark(Long userId, Long postId, BookmarkFormDTO form) {
    if(userId != null) {
      Optional<Post> post = postService.findOne(postId);

      if(post.isPresent()) {
        Bookmark bookmark = new Bookmark();
        bookmark.setUserId(form.getUserId());
        bookmark.setPostId(form.getPostId());

        bookmarkService.saveOne(bookmark);

        return new ResponseEntity<>("북마크를 등록했습니다.", HttpStatus.OK);
      }
      else {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시물이 존재하지 않습니다.");
      }
    }
    else {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요한 기능입니다. 로그인 후 이용해주세요");
    }
  }

  // 북마크 조회: Complete
  // 마이페이지에 접속했을 때 실행
  @GetMapping("bookmark-load")
  @ResponseBody
  public Pair<Integer, List<Post>> loadBookmark(Long userId, Long pageNumber) {
    if (userId != null) {
      int pageSize = 3;
      int totalPages = bookmarkService.findTotalPages(userId);
      totalPages = (int) Math.ceil((double) totalPages / pageSize);
      
      List<Bookmark> bookmarkList = bookmarkService.findAll(userId, pageNumber);
      List<Post> postList = new ArrayList<>();
  
      for(Bookmark bookmarkIndex : bookmarkList) {
        Long postId = bookmarkIndex.getPostId();
        Optional<Post> post = postService.findOne(postId);
        postList.add(post.get());
  
        // if(post.isPresent()) {
        //   postList.add(post.get());
        // }
        // else {
        //   Long bookmarkId = bookmarkIndex.getBookmarkId();
        //   bookmarkService.deleteOne(bookmarkId);
        // }
      }
  
      return Pair.of(totalPages, postList);
    }
    else {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요한 기능입니다. 로그인 후 이용해주세요");
    }
  }

  // 북마크 삭제: Complete
  // 1. 해당 포스트 내에서 실행
  // 2. 마이페이지에 접속했을 때 실행
  @PostMapping("bookmark-delete")
  @ResponseBody
  public ResponseEntity<String> deleteBookmark(Long userId, Long bookmarkId) {
    if ( userId != null) {
      Optional<Bookmark> bookmark = bookmarkService.findOne(bookmarkId);
  
      if(bookmark.isPresent()) {
        if(Objects.equals(bookmark.get().getUserId(), userId)) {
          bookmarkService.deleteOne(bookmarkId);
          return new ResponseEntity<>("북마크를 삭제했습니다.", HttpStatus.OK);
        }
        else {
          throw new ResponseStatusException(HttpStatus.FORBIDDEN, "북마크를 삭제할 수 없습니다.");
        }
      }
      else {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "북마크를 찾을 수 없습니다.");
      }
    }
    else {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요한 기능입니다. 로그인 후 이용해주세요");
    }
  }
}
