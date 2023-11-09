package com.release.core;

import com.release.core.bookmark.repository.BookmarkRepository;
import com.release.core.bookmark.repository.JpaBookmarkRepository;
import com.release.core.bookmark.service.BookmarkService;
import com.release.core.repository.*;
import com.release.core.service.UserService;
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
        return new UserService(userRepository());
    }
    @Bean
    public UserRepository userRepository() {
        //return new MemoryUserRepository();
        //return new JdbcTemplateUserRepository(dataSource);
        return new JPAUserRepository(em);
    }

    // Post
    @Bean
    public PostRepository postRepository() {
        return new JPAPostRepository(em);
    }

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
