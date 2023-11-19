package com.release.core.controller;

import com.release.core.domain.Post;
import com.release.core.service.PostService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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


    // 게시물 등록
    @GetMapping("post-write")
    @ResponseBody
    public Post postWrite(@CookieValue(name="userId", required = false) Long userId,
                          @RequestParam("postTitle") String postTitle,
                          @RequestParam("postDate") String postDate,
                          @RequestParam("content") String content,
                          @RequestParam("tripDays") Long tripDays,
                          @RequestParam("tagId") List<Long> tagIdList) {
        Post post = new Post();
        post.setPostTitle(postTitle);
        post.setPostDate(postDate);
        post.setPostContent(content);
        post.setPostTripDays(tripDays);
        post.setWriterUserId(userId);
        post.setTagIdList(tagIdList);

        postService.write(post, tagIdList);
        return post;
    }

    // 게시물 조회
    @GetMapping("post-delete")
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
        Optional<Post> post = postService.findOne(postId);
        if(post.isPresent()) {
            return post.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }
    }
}
