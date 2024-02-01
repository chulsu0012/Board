package com.release.core.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.release.core.dto.PostListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.server.ResponseStatusException;

import com.release.core.domain.Post;
import com.release.core.dto.PostEditFormDTO;
import com.release.core.dto.PostWriteFormDTO;
import com.release.core.service.BookmarkService;
import com.release.core.service.PostService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PostController {
    private final PostService postService;
    private final BookmarkService bookmarkService;

    @Autowired
    public PostController(PostService postService, BookmarkService bookmarkService) {
        this.postService = postService;
        this.bookmarkService = bookmarkService;
    }


    @GetMapping("login-dev")
    public ResponseEntity<String> login_dev(HttpServletRequest request,
                                       @RequestParam("userId") Long userId) {

        HttpSession session = request.getSession();
        session.setAttribute("userId", userId);

        return new ResponseEntity<>("Welcome user " + userId, HttpStatus.OK);
    }

    @GetMapping("logout-dev")
    public ResponseEntity<String> logout_dev(HttpServletRequest request,
                                             @SessionAttribute(name="userId", required=false) Long oldUserId) {
        return new ResponseEntity<>("Goodbye user " + oldUserId, HttpStatus.OK);
    }

    @GetMapping("post-search")
    @ResponseBody
    public PostListResponse postSearch(HttpServletRequest request,
                                       @SessionAttribute(name="userId", required=false) Long userId,
                                       @RequestParam("page") int page,
                                       @RequestParam(value = "tagId", required = false) List<Long> tagIdList,
                                       @RequestParam(value = "tripDays", required = false) Long tripDays) {
        return new PostListResponse(postService.getAllPageSearch(tagIdList, tripDays), postService.search(tagIdList, page, tripDays, userId));
    }

    @GetMapping("post-search-with-query")
    @ResponseBody
    public PostListResponse postSearchWithQuery(HttpServletRequest request,
                                                @SessionAttribute(name="userId", required=false) Long userId,
                                                @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                @RequestParam("query") String query) {
        return new PostListResponse(postService.getAllpageSearchWithQuery(query), postService.searchWithQuery(query, page, userId));
    }

    // 게시물 등록
    @PostMapping("post-write")
    @ResponseBody
    public Post postWrite(@SessionAttribute(name="userId", required=false) Long userId,
                          PostWriteFormDTO form) {
        Post post = new Post();
        post.setPostTitle(form.getPostTitle());
        post.setPostDate(form.getPostDate());
        post.setPostContent(form.getContent());
        post.setPostTripDays(form.getTripDays());
        post.setWriterUserId(userId);
        post.setTagIdList(form.getTagIdList());
        postService.write(post, form.getTagIdList());
        post = postService.applyTransientData(post, userId);
        return post;
    }

    @PutMapping("post-edit")
    @ResponseBody
    public ResponseEntity<String> postEdit(@SessionAttribute(name="userId", required=false) Long userId,
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
    public ResponseEntity<String> postDelete(@SessionAttribute(name="userId", required=false) Long userId,
                                             @RequestParam("postId") Long postId) {
        Optional<Post> post = postService.findOne(postId);
        if(post.isPresent()) {
            if(Objects.equals(post.get().getWriterUserId(), userId)) {
                postService.deletePost(postId);
                bookmarkService.deleteAllRelatedOne(postId); // 해당 게시물의 북마크들 삭제
                
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
    public Post postRead(@RequestParam("postId") Long postId,
                         @SessionAttribute(name="userId", required=false) Long userId) {
        Optional<Post> optionalPost = postService.findOne(postId);
        if(optionalPost.isPresent()) {
            Post post = postService.applyTransientData(optionalPost.get(), userId);
            return post;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }
    }
}
