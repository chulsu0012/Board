package com.release.core.service;

import com.release.core.domain.Post;
import com.release.core.domain.User;
import com.release.core.repository.PostRepository;
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
    public PostService(PostRepository postRepository) {this.postRepository = postRepository;}

    public Long write(Post post) {
        boolean isValid = checkValidatePost(post);
        if(isValid) {
            postRepository.save(post);
            return post.getPostId();
        } else {return null;}
    }

    public boolean checkValidatePost(Post post) {
        return true;
    }

    public Optional<Post> findOne(Long postId) {return postRepository.findById(postId);}

    public List<Post> findAll() {return postRepository.getAllPosts();}

    public List<Post> findAll(int num) {
        List<Post> allPosts = findAll();
        if(allPosts.size() <= num) {return allPosts;}
        else {
            return allPosts.stream().limit(num).collect(Collectors.toList());
        }
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
}
