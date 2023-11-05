package com.release.core.bookmark.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.release.core.bookmark.repository.BookmarkRepository;

@Configuration
public class SpringConfig {
  private final BookmarkRepository bookmarkRepository;

  public SpringConfig(BookmarkRepository bookmarkRepository) {
    this.bookmarkRepository = bookmarkRepository;
  }
  
  @Bean
  public BookmarkService bookmarkService() {
    return new BookmarkService(bookmarkRepository);
  }
}
