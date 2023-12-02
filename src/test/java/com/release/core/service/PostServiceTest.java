package com.release.core.service;

import com.release.core.domain.Category;
import com.release.core.domain.Post;
import com.release.core.domain.Tag;
import com.release.core.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootTest
@Transactional
public class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    PostTagsConnectionRepository postTagsConnectionRepository;

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

        Assertions.assertEquals(postService.findAll(0, num).size(), 2);
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

        Assertions.assertEquals(postService.findAll(0, num).size(), num);
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

    @Test
    void GetPostWithTagList() {
        Category category1 = new Category();
        category1.setCategoryName("경기도");
        categoryRepository.save(category1);
        Category category2 = new Category();
        category2.setCategoryName("충청도");
        categoryRepository.save(category2);

        Tag tag1 = new Tag();
        tag1.setTagName("의정부시");
        tag1.setTagParentId(category1.getCategoryId());
        tagRepository.save(tag1);
        Tag tag2 = new Tag();
        tag2.setTagName("성남시");
        tag2.setTagParentId(category1.getCategoryId());
        tagRepository.save(tag2);
        Tag tag3 = new Tag();
        tag3.setTagName("천안시");
        tag3.setTagParentId(category2.getCategoryId());;
        tagRepository.save(tag3);
        Tag tag4 = new Tag();
        tag4.setTagName("공주시");
        tag4.setTagParentId(category2.getCategoryId());
        tagRepository.save(tag4);
        Tag tag5 = new Tag();
        tag5.setTagName("아산시");
        tag5.setTagParentId(category2.getCategoryId());
        tagRepository.save(tag5);

        ArrayList<Long> tagIdList1 = new ArrayList<>();
        tagIdList1.add(tag1.getTagId());
        tagIdList1.add(tag2.getTagId());
        tagIdList1.add(tag3.getTagId());
        ArrayList<Long> tagIdList2 = new ArrayList<>();
        tagIdList2.add(tag2.getTagId());
        tagIdList2.add(tag4.getTagId());
        tagIdList2.add(tag5.getTagId());

        Post post1 = new Post();
        post1.setPostTitle("테스트 제목1");
        post1.setPostContent("테스트 내용1");
        post1.setPostDate("2023-11-10");
        post1.setPostTripDays(3L);
        post1.setWriterUserId(1L);
        postService.write(post1, tagIdList1);

        Post post2 = new Post();
        post2.setPostTitle("테스트 제목2");
        post2.setPostContent("테스트 내용2");
        post2.setPostDate("2023-11-12");
        post2.setPostTripDays(4L);
        post2.setWriterUserId(1L);
        postService.write(post2, tagIdList2);

        ArrayList<Long> searchTagIdList1 = new ArrayList<>();
        searchTagIdList1.add(tag3.getTagId());
        ArrayList<Long> searchTagIdList2 = new ArrayList<>();
        searchTagIdList2.add(tag3.getTagId());
        searchTagIdList2.add(tag4.getTagId());
        ArrayList<Long> searchTagIdList3 = new ArrayList<>();
        searchTagIdList3.add(tag2.getTagId());
        ArrayList<Long> searchTagIdList4 = new ArrayList<>();
        searchTagIdList4.add(tag5.getTagId());
        ArrayList<Long> searchTagIdList5 = new ArrayList<>();
        searchTagIdList5.add(tag2.getTagId());
        searchTagIdList5.add(tag4.getTagId());
        searchTagIdList5.add(tag5.getTagId());

        Assertions.assertEquals(postService.findByTag(searchTagIdList1).size(), 1);
        Assertions.assertEquals(postService.findByTag(searchTagIdList2).size(), 2);
        Assertions.assertEquals(postService.findByTag(searchTagIdList3).size(), 2);
        Assertions.assertEquals(postService.findByTag(searchTagIdList4).size(), 1);
        Assertions.assertEquals(postService.findByTag(searchTagIdList5).size(), 2);
        Assertions.assertEquals(postService.findByTag(searchTagIdList2, 0, 0).size(), 0);
        Assertions.assertEquals(postService.findByTag(searchTagIdList2, 1, 0).size(), 0);
        Assertions.assertEquals(postService.findByTag(searchTagIdList3, 1, 2).size(), 1);
        Assertions.assertEquals(postService.findByTag(searchTagIdList5, 0, 1).size(), 1);
        Assertions.assertEquals(postService.findByTag(searchTagIdList5, 5, 7).size(), 0);
    }

    @Test
    void DeletePost() {
        Category category1 = new Category();
        category1.setCategoryName("경기도");
        categoryRepository.save(category1);
        Category category2 = new Category();
        category2.setCategoryName("충청도");
        categoryRepository.save(category2);

        Tag tag1 = new Tag();
        tag1.setTagName("의정부시");
        tag1.setTagParentId(category1.getCategoryId());
        tagRepository.save(tag1);
        Tag tag2 = new Tag();
        tag2.setTagName("성남시");
        tag2.setTagParentId(category1.getCategoryId());
        tagRepository.save(tag2);
        Tag tag3 = new Tag();
        tag3.setTagName("천안시");
        tag3.setTagParentId(category2.getCategoryId());;
        tagRepository.save(tag3);
        Tag tag4 = new Tag();
        tag4.setTagName("공주시");
        tag4.setTagParentId(category2.getCategoryId());
        tagRepository.save(tag4);
        Tag tag5 = new Tag();
        tag5.setTagName("아산시");
        tag5.setTagParentId(category2.getCategoryId());
        tagRepository.save(tag5);

        ArrayList<Long> tagIdList1 = new ArrayList<>();
        tagIdList1.add(tag1.getTagId());
        tagIdList1.add(tag2.getTagId());
        tagIdList1.add(tag3.getTagId());
        ArrayList<Long> tagIdList2 = new ArrayList<>();
        tagIdList2.add(tag2.getTagId());
        tagIdList2.add(tag4.getTagId());
        tagIdList2.add(tag5.getTagId());

        Post post1 = new Post();
        post1.setPostTitle("테스트 제목1");
        post1.setPostContent("테스트 내용1");
        post1.setPostDate("2023-11-10");
        post1.setPostTripDays(3L);
        post1.setWriterUserId(1L);
        postService.write(post1, tagIdList1);

        Post post2 = new Post();
        post2.setPostTitle("테스트 제목2");
        post2.setPostContent("테스트 내용2");
        post2.setPostDate("2023-11-12");
        post2.setPostTripDays(4L);
        post2.setWriterUserId(1L);
        postService.write(post2, tagIdList2);

        ArrayList<Long> searchTagIdList1 = new ArrayList<>();
        searchTagIdList1.add(tag3.getTagId());
        ArrayList<Long> searchTagIdList2 = new ArrayList<>();
        searchTagIdList2.add(tag3.getTagId());
        searchTagIdList2.add(tag4.getTagId());
        ArrayList<Long> searchTagIdList3 = new ArrayList<>();
        searchTagIdList3.add(tag2.getTagId());
        ArrayList<Long> searchTagIdList4 = new ArrayList<>();
        searchTagIdList4.add(tag5.getTagId());
        ArrayList<Long> searchTagIdList5 = new ArrayList<>();
        searchTagIdList5.add(tag2.getTagId());
        searchTagIdList5.add(tag4.getTagId());
        searchTagIdList5.add(tag5.getTagId());

        Long postId1 = post1.getPostId();
        Long postId2 = post2.getPostId();

        postService.deletePost(post1.getPostId());

        Assertions.assertEquals(postService.findByTag(searchTagIdList1).size(), 0);
        Assertions.assertEquals(postService.findByTag(searchTagIdList2).size(), 1);
        Assertions.assertEquals(postService.findByTag(searchTagIdList3).size(), 1);
        Assertions.assertEquals(postService.findByTag(searchTagIdList4).size(), 1);
        Assertions.assertEquals(postService.findByTag(searchTagIdList5).size(), 1);
        Assertions.assertTrue(postService.findOne(postId1).isEmpty());
        Assertions.assertFalse(postService.findOne(postId2).isEmpty());
    }


}