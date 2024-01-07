package com.release.core;

import javax.sql.DataSource;

import com.release.core.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.release.core.service.BookmarkService;
import com.release.core.service.PostService;

import jakarta.persistence.EntityManager;

@Configuration
public class AppConfig{

    private final DataSource dataSource;
    private final EntityManager em;

    private final UserRepository userRepository;


    public AppConfig(DataSource dataSource, EntityManager em, UserRepository userRepository) {
        this.dataSource = dataSource;
        this.em = em;
        this.userRepository = userRepository;
    }


    // Category
    @Bean
    public CategoryRepository categoryRepository() {return new JPACategoryRepository(em);}

    // Tag
    @Bean
    public TagRepository tagRepository() {return new JPATagRepository(em);}

    // PostTagsConnection
    @Bean
    public PostTagsConnectionRepository postTagsConnectionRepository() {return new JPAPostTagsConnectionRepository(em);}


    // Post
    @Bean
    public PostRepository postRepository() {
        return new JPAPostRepository(em);
    }

    @Bean
    public PostService postService() {return new PostService(postRepository(), postTagsConnectionRepository(), tagRepository(), categoryRepository(), userRepository);}


    // Bookmark
    @Bean
    public BookmarkService bookmarkService() {
      return new BookmarkService(bookmarkRepository());
    }

    @Bean
    public BookmarkRepository bookmarkRepository() {
      return new JPABookmarkRepository(em);
    }




}
