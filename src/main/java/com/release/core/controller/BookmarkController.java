package com.release.core.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.server.ResponseStatusException;

import com.release.core.domain.Bookmark;
import com.release.core.domain.Post;
import com.release.core.dto.BookmarkDeleteRequest;
import com.release.core.dto.BookmarkSaveRequest;
import com.release.core.service.BookmarkService;
import com.release.core.service.PostService;


@RestController
@RequestMapping("/bookmark")
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
  @PostMapping("save")
  public ResponseEntity<String> saveBookmark(@SessionAttribute(name="userId", required=false) Long userId, @RequestBody BookmarkSaveRequest form) {
    if (userId != null) {
      Long postId = form.getPostId();

      if (postId != null) {
        Optional<Post> post = postService.findOne(postId);
        
        if(post.isPresent()) {
          Bookmark bookmark = new Bookmark();
          bookmark.setUserId(userId);
          bookmark.setPostId(postId);
          bookmarkService.saveOne(bookmark);
  
          return new ResponseEntity<>("북마크를 등록했습니다.", HttpStatus.OK);
        }
        else {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND, "게시물이 존재하지 않습니다.");
        }
      }
      else {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "게시물 정보를 처리하는 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요."+postId);
      }
    }
    else {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요한 기능입니다. 로그인 후 이용해주세요");
    }
  }

  // 북마크 조회: Complete
  // 마이페이지에 접속했을 때 실행
  @GetMapping("load")
  public Pair<Integer, List<Post>> loadBookmark(@SessionAttribute(name="userId", required=false) Long userId, Long pageNumber) {
    if (userId != null) {
      Long pageSize = 30L;
      int totalPages = bookmarkService.findTotalPages(userId);
      totalPages = (int)Math.ceil((double) totalPages / pageSize.intValue());
      
      List<Bookmark> bookmarkList = bookmarkService.findAll(userId, pageSize, pageNumber);
      List<Post> postList = new ArrayList<>();
  
      for(Bookmark bookmarkIndex : bookmarkList) {
        Long postId = bookmarkIndex.getPostId();
        Optional<Post> post = postService.findOne(postId);
        if(post.isPresent()) {
          Post p = postService.applyTransientData(post.get(), userId);
          postList.add(p);
        }
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
  @PostMapping("delete")
  public ResponseEntity<String> deleteBookmark(@SessionAttribute(name="userId", required=false) Long userId, @RequestBody BookmarkDeleteRequest form) {
    if (userId != null) {
      Long bookmarkId = form.getBookmarkId();

      if (bookmarkId != null) {
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
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "북마크 정보를 처리하는 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
      }
    }
    else {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요한 기능입니다. 로그인 후 이용해주세요");
    }
  }
}
