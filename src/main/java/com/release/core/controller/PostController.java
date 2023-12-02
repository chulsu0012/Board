package com.release.core.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.release.core.domain.Post;
import com.release.core.dto.PostEditFormDTO;
import com.release.core.dto.PostWriteFormDTO;
import com.release.core.service.PostService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.ibatis.annotations.Delete;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class PostController {
    private final PostService postService;
    @Autowired
    public PostController(PostService postService) {this.postService = postService;}

    @GetMapping("login-dev")
    public ResponseEntity<String> home(HttpServletResponse response,
                                       @RequestParam("userId") Long userId) {
        Cookie userIdCookie = new Cookie("userId", String.valueOf(userId));
        response.addCookie(userIdCookie);
        return new ResponseEntity<>("Welcome user " + userId, HttpStatus.OK);
    }

    @GetMapping("post-search")
    @ResponseBody
    public List<Post> postSearch(@CookieValue(name="userId", required = false) Long userId,
                                 @RequestParam("tagId") List<Long> tagIdList,
                                 @RequestParam("page") Long page,
                                 @RequestParam("tripDays") Long tripDays) {
        return postService.search(tagIdList, page, tripDays);
    }

    // 게시물 등록
    @PostMapping("post-write")
    @ResponseBody
    public Post postWrite(@CookieValue(name="userId", required = false) Long userId,
                          PostWriteFormDTO form) {
        Post post = new Post();
        post.setPostTitle(form.getPostTitle());
        post.setPostDate(form.getPostDate());
        post.setPostContent(form.getContent());
        post.setPostTripDays(form.getTripDays());
        post.setWriterUserId(userId);
        post.setTagIdList(form.getTagIdList());
        postService.write(post, form.getTagIdList());
        return post;
    }

    @PutMapping("post-edit")
    @ResponseBody
    public ResponseEntity<String> postEdit(@CookieValue(name="userId", required = false) Long userId,
                          PostEditFormDTO form) {

        Optional<Post> postOptional = postService.findOne(form.getPostId());
        if(postOptional.isPresent()) {
            if(Objects.equals(postOptional.get().getWriterUserId(), userId)) {
                postService.editPost(postOptional.get(), form);
                return new ResponseEntity<>("Edit the post completely.", HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access Denied");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }
    }


    // 게시물 삭제
    @DeleteMapping("post-delete")
    @ResponseBody
    public ResponseEntity<String> postDelete(@CookieValue(name="userId", required = false) Long userId,
                            @RequestParam("postId") Long postId) {
        Optional<Post> post = postService.findOne(postId);
        if(post.isPresent()) {
            if(Objects.equals(post.get().getWriterUserId(), userId)) {
                postService.deletePost(postId);
                return new ResponseEntity<>("Delete the post completely.", HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access Denied");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }
    }

    // 게시물 조회
    @GetMapping("post-read")
    @ResponseBody
    public Post postRead(@RequestParam("postId") Long postId) {
        Optional<Post> optionalPost = postService.findOne(postId);
        if(optionalPost.isPresent()) {
            Post post = postService.applyTagIdList(optionalPost.get());
            return post;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }
    }
}
