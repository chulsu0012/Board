package com.release.core;

import com.release.core.bookmark.repository.BookmarkRepository;
import com.release.core.bookmark.repository.JpaBookmarkRepository;
import com.release.core.bookmark.service.BookmarkService;
import com.release.core.repository.*;
import com.release.core.service.PostService;
import com.release.core.service.UserService;
import com.release.core.service.UserServiceImpl;
import jakarta.persistence.EntityManager;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class AppConfig{

    private final DataSource dataSource;
    private final EntityManager em;

    public AppConfig(DataSource dataSource, EntityManager em) {
        this.dataSource = dataSource;
        this.em = em;
    }
    // User
    @Bean
    public UserService userService() {
        return new UserServiceImpl(userRepository());
    }
    @Bean
    public UserRepository userRepository() {
        //return new MemoryUserRepository();
        //return new JdbcTemplateUserRepository(dataSource);
        return new JPAUserRepository(em);
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
    public PostService postService() {return new PostService(postRepository(), postTagsConnectionRepository(), tagRepository(), categoryRepository());}


    // Bookmark
    @Bean
    public BookmarkService bookmarkService() {
      return new BookmarkService(bookmarkRepository());
    }

    @Bean
    public BookmarkRepository bookmarkRepository() {
      return new JpaBookmarkRepository(em);
    }

}
