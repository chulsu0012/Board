package com.release.core.service;

import com.release.core.domain.Post;
import com.release.core.repository.JPAPostRepository;
import com.release.core.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest
@Transactional
public class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Test
    void GetPostWithNum() throws Exception {
        int num = 3;

        Post post1 = new Post();
        post1.setPostTitle("테스트 제목1");
        post1.setPostContent("테스트 내용1");
        post1.setPostDate("2023-11-12");
        post1.setPostTripDays(3L);
        post1.setWriterUserId(1L);
        postRepository.save(post1);

        Post post2 = new Post();
        post2.setPostTitle("테스트 제목2");
        post2.setPostContent("테스트 내용2");
        post2.setPostDate("2023-11-12");
        post2.setPostTripDays(4L);
        post2.setWriterUserId(1L);
        postRepository.save(post2);

        Assertions.assertEquals(postService.findAll(num).size(), 2);
        Assertions.assertEquals(postService.findAll().size(), 2);

        Post post3 = new Post();
        post3.setPostTitle("테스트 제목3");
        post3.setPostContent("테스트 내용3");
        post3.setPostDate("2023-11-12");
        post3.setPostTripDays(4L);
        post3.setWriterUserId(1L);
        postRepository.save(post3);

        Post post4 = new Post();
        post4.setPostTitle("테스트 제목4");
        post4.setPostContent("테스트 내용4");
        post4.setPostDate("2023-11-12");
        post4.setPostTripDays(5L);
        post4.setWriterUserId(1L);
        postRepository.save(post4);

        Assertions.assertEquals(postService.findAll(num).size(), num);
        Assertions.assertEquals(postService.findAll().size(), 4);
    }

    @Test
    void GetPostWithTripDays() throws Exception {
        Post post1 = new Post();
        post1.setPostTitle("테스트 제목1");
        post1.setPostContent("테스트 내용1");
        post1.setPostDate("2023-11-12");
        post1.setPostTripDays(3L);
        post1.setWriterUserId(1L);
        postRepository.save(post1);

        Post post2 = new Post();
        post2.setPostTitle("테스트 제목2");
        post2.setPostContent("테스트 내용2");
        post2.setPostDate("2023-11-12");
        post2.setPostTripDays(4L);
        post2.setWriterUserId(1L);
        postRepository.save(post2);

        Post post3 = new Post();
        post3.setPostTitle("테스트 제목3");
        post3.setPostContent("테스트 내용3");
        post3.setPostDate("2023-11-12");
        post3.setPostTripDays(4L);
        post3.setWriterUserId(1L);
        postRepository.save(post3);

        Post post4 = new Post();
        post4.setPostTitle("테스트 제목4");
        post4.setPostContent("테스트 내용4");
        post4.setPostDate("2023-11-12");
        post4.setPostTripDays(5L);
        post4.setWriterUserId(1L);
        postRepository.save(post4);

        Assertions.assertEquals(postService.findByTripDays(3L).size(), 1);
        Assertions.assertEquals(postService.findByTripDays(4L).size(), 2);
        Assertions.assertEquals(postService.findByTripDays(5L).size(), 1);
    }

    @Test
    void GetPostWithSinglePostDate() throws Exception {
        LocalDate testDate1 = LocalDate.of(2023, 11, 10);
        LocalDate testDate2 = LocalDate.of(2023, 11, 12);
        LocalDate testDate3 = LocalDate.of(2023, 11, 16);

        Post post1 = new Post();
        post1.setPostTitle("테스트 제목1");
        post1.setPostContent("테스트 내용1");
        post1.setPostDate("2023-11-10");
        post1.setPostTripDays(3L);
        post1.setWriterUserId(1L);
        postRepository.save(post1);

        Post post2 = new Post();
        post2.setPostTitle("테스트 제목2");
        post2.setPostContent("테스트 내용2");
        post2.setPostDate("2023-11-12");
        post2.setPostTripDays(4L);
        post2.setWriterUserId(1L);
        postRepository.save(post2);

        Post post3 = new Post();
        post3.setPostTitle("테스트 제목3");
        post3.setPostContent("테스트 내용3");
        post3.setPostDate("2023-11-12");
        post3.setPostTripDays(4L);
        post3.setWriterUserId(1L);
        postRepository.save(post3);

        Post post4 = new Post();
        post4.setPostTitle("테스트 제목4");
        post4.setPostContent("테스트 내용4");
        post4.setPostDate("2023-11-16");
        post4.setPostTripDays(5L);
        post4.setWriterUserId(1L);
        postRepository.save(post4);

        Assertions.assertEquals(postService.findByPostDate(testDate1).size(), 1);
        Assertions.assertEquals(postService.findByPostDate(testDate2).size(), 2);
        Assertions.assertEquals(postService.findByPostDate(testDate3).size(), 1);
    }

    @Test
    void GetPostWithDoublePostDate() throws Exception {
        LocalDate startTestDate1 = LocalDate.of(2023, 11, 10);
        LocalDate endTestDate1 = LocalDate.of(2023, 11, 12);

        LocalDate startTestDate2 = LocalDate.of(2023, 11, 12);
        LocalDate endTestDate2 = LocalDate.of(2023, 11, 16);

        LocalDate startTestDate3 = LocalDate.of(2023, 11, 10);
        LocalDate endTestDate3 = LocalDate.of(2023, 11, 16);

        Post post1 = new Post();
        post1.setPostTitle("테스트 제목1");
        post1.setPostContent("테스트 내용1");
        post1.setPostDate("2023-11-10");
        post1.setPostTripDays(3L);
        post1.setWriterUserId(1L);
        postRepository.save(post1);

        Post post2 = new Post();
        post2.setPostTitle("테스트 제목2");
        post2.setPostContent("테스트 내용2");
        post2.setPostDate("2023-11-12");
        post2.setPostTripDays(4L);
        post2.setWriterUserId(1L);
        postRepository.save(post2);

        Post post3 = new Post();
        post3.setPostTitle("테스트 제목3");
        post3.setPostContent("테스트 내용3");
        post3.setPostDate("2023-11-12");
        post3.setPostTripDays(4L);
        post3.setWriterUserId(1L);
        postRepository.save(post3);

        Post post4 = new Post();
        post4.setPostTitle("테스트 제목4");
        post4.setPostContent("테스트 내용4");
        post4.setPostDate("2023-11-16");
        post4.setPostTripDays(5L);
        post4.setWriterUserId(1L);
        postRepository.save(post4);

        Assertions.assertEquals(postService.findByPostDate(startTestDate1, endTestDate1).size(), 3);
        Assertions.assertEquals(postService.findByPostDate(startTestDate2, endTestDate2).size(), 3);
        Assertions.assertEquals(postService.findByPostDate(startTestDate3, endTestDate3).size(), 4);
    }


}
