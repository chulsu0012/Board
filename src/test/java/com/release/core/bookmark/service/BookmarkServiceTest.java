package com.release.core.bookmark.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.release.core.bookmark.domain.Bookmark;
import com.release.core.bookmark.repository.BookmarkRepository;

@SpringBootTest
@Transactional
class BookmarkServiceTest {

  @Autowired BookmarkService bookmarkService;
  @Autowired BookmarkRepository bookmarkRepository;

  
  @Test
  void testDoBookmark() {
    //given
    Bookmark bookmark = new Bookmark();
    bookmark.setBookmarkId(10L);
    bookmark.setPostId(10L);
    bookmark.setUserId(100L);
    
    //when
    Long saveId = bookmarkService.save(bookmark);
    
    //then
    Bookmark findBookmark = bookmarkService.find(saveId).get();
    assertEquals(bookmark.getBookmarkId(), findBookmark.getBookmarkId());
  }

  @Test
  void testFindOne() {
    //given
    Bookmark bookmark1 = new Bookmark();
    bookmark1.setBookmarkId(1L);
    
    Bookmark bookmark2 = new Bookmark();
    bookmark2.setBookmarkId(1L);
    
    //when
    bookmarkService.save(bookmark1);
    IllegalStateException e = assertThrows(IllegalStateException.class, () -> bookmarkService.save(bookmark2));
    
    //then
    assertThat(e.getMessage()).isEqualTo("이미 북마크에 등록된 게시물입니다.");
  }
  
  @Test
  void testLoadBookmark() {
    //given
    
    //when
    
    //then
  }
  
  @Test
  void testDeleteBookmark() {
    //given
    
    //when
    
    //then
  }
}
