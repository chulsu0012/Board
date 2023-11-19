package com.release.core.service;

import com.release.core.domain.Post;
import com.release.core.domain.PostTagsConnection;
import com.release.core.domain.User;
import com.release.core.repository.CategoryRepository;
import com.release.core.repository.PostRepository;
import com.release.core.repository.PostTagsConnectionRepository;
import com.release.core.repository.TagRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final PostTagsConnectionRepository postTagsConnectionRepository;
    private final TagRepository tagRepository;

    private final CategoryRepository categoryRepository;


    public PostService(PostRepository postRepository, PostTagsConnectionRepository postTagsConnectionRepository, TagRepository tagRepository, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.postTagsConnectionRepository = postTagsConnectionRepository;
        this.tagRepository = tagRepository;
        this.categoryRepository = categoryRepository;
    }

    public static <T> List<T> getPartOfList(List<T> list, int start, int end) {
        if(start >= end) {return new ArrayList<T>();}
        if(list.size() < start) {return new ArrayList<T>();}
        else {
            if(list.size() < end) {
                end = list.size();
            }

            ArrayList<T> result = new ArrayList<>();
            for(int i=0; i<end; i++) {
                if(i >= start) {
                    result.add(list.get(i));
                }
            }

            return result;
        }
    }

    public Long write(Post post, List<Long> tagIdList) {
        boolean isValid = checkValidatePost(post);
        if(isValid) {
            postRepository.save(post);

            for(Long tagId : tagIdList) {
                PostTagsConnection postTagConnection = new PostTagsConnection();
                postTagConnection.setPostId(post.getPostId());
                postTagConnection.setTagId(tagId);
                postTagsConnectionRepository.save(postTagConnection);
            }

            return post.getPostId();
        } else {return null;}
    }

    public boolean checkValidatePost(Post post) {
        return true;
    }

    public Optional<Post> findOne(Long postId) {return postRepository.findById(postId);}

    public List<Post> findAll() {return postRepository.getAllPosts();}

    public List<Post> findAll(int start, int end) {
        List<Post> allPosts = findAll();

        return getPartOfList(allPosts, start, end);
    }

    public List<Post> findByTripDays(Long tripDays) {return postRepository.findByTripDays(tripDays);}

    public List<Post> findByPostDate(LocalDate date) {
        String dateString = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        return postRepository.findByPostDate(dateString);
    }

    public List<Post> findByPostDate(LocalDate startDate, LocalDate endDate) {
        List<Post> result = new ArrayList<Post>();

        long days = ChronoUnit.DAYS.between(startDate, endDate);
        for(int delta=0; delta<=days; delta++) {
            LocalDate iterDate = startDate.plusDays(delta);
            result.addAll(findByPostDate(iterDate));
        }

        return result;
    }

    public List<Post> findByTag(List<Long> tagIdList) {
        ArrayList<Post> result = new ArrayList<>();
        for(Long tagId : tagIdList) {
            List<PostTagsConnection> connectionList = postTagsConnectionRepository.findByTagId(tagId);

            for(PostTagsConnection postTagConnection : connectionList) {
                Post post = postRepository.findById(postTagConnection.getPostId()).get();
                if(!result.contains(post)) {
                    result.add(post);
                }
            }
        }
        return result;
    }

    public List<Post> findByTag(List<Long> tagIdList, int start, int end) {
        return getPartOfList(findByTag(tagIdList), start, end);
    }
}
