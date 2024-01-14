package com.release.core.service;

import com.release.core.domain.Post;
import com.release.core.domain.PostTagsConnection;
import com.release.core.domain.User;
import com.release.core.dto.PostEditFormDTO;
import com.release.core.repository.*;
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
    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;


    public PostService(PostRepository postRepository, PostTagsConnectionRepository postTagsConnectionRepository, TagRepository tagRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.postTagsConnectionRepository = postTagsConnectionRepository;
        this.tagRepository = tagRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
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

    public Post applyTransientData(Post post) {
        Optional<User> optionalWriterUser = userRepository.findByUserId(post.getWriterUserId());
        if(optionalWriterUser.isPresent()) {
            post.setWriterUserName(optionalWriterUser.get().getUserName());
        }

        List<PostTagsConnection> connectionList = postTagsConnectionRepository.findByPostId(post.getPostId());
        ArrayList<Long> tagIdList = new ArrayList<>();
        for(PostTagsConnection connection : connectionList) {
            tagIdList.add(connection.getTagId());
        }
        post.setTagIdList(tagIdList);

        return post;
    }

    public Long write(Post post, List<Long> tagIdList) {
        boolean isValid = checkValidatePost(post);
        if(isValid) {
            post.setTagIdList(tagIdList);
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

    public boolean editPost(Post post, PostEditFormDTO form) {
        post = applyTransientData(post);
        List<PostTagsConnection> oldPostTagsConnectionOptional = postTagsConnectionRepository.findByPostId(post.getPostId());
        for(PostTagsConnection connection : oldPostTagsConnectionOptional) {postTagsConnectionRepository.delete(connection.getConnectionId());}

        post.setPostTitle(form.getPostTitle());
        post.setPostDate(form.getPostDate());
        post.setPostContent(form.getContent());
        post.setPostTripDays(form.getTripDays());
        postRepository.save(post);

        for(Long tagId : form.getTagIdList()) {
            PostTagsConnection postTagConnection = new PostTagsConnection();
            postTagConnection.setPostId(post.getPostId());
            postTagConnection.setTagId(tagId);
            postTagsConnectionRepository.save(postTagConnection);
        }

        return true;
    }

    public boolean checkValidatePost(Post post) {
        return true;
    }

    public Optional<Post> findOne(Long postId) {return postRepository.findById(postId);}


    public List<Post> findByTripDays(Long tripDays, int start) {
        return postRepository.findByTripDays(tripDays, start);
    }

    public List<Post> findByTripDays(Long tripDays) {
        return findByTripDays(tripDays, 0);
    }

    public List<Post> findByPostDate(LocalDate date, int start) {
        String dateString = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        return postRepository.findByPostDate(dateString, start);
    }

    public List<Post> findByPostDate(LocalDate date) {
        return findByPostDate(date, 0);
    }

    public List<Post> findByPostDate(LocalDate startDate, LocalDate endDate, int start) {
        List<Post> result = new ArrayList<Post>();

        long days = ChronoUnit.DAYS.between(startDate, endDate);
        for(int delta=0; delta<=days; delta++) {
            LocalDate iterDate = startDate.plusDays(delta);
            result.addAll(findByPostDate(iterDate, start));
        }
        return result;
    }

    public List<Post> findByPostDate(LocalDate startDate, LocalDate endDate) {
        return findByPostDate(startDate, endDate, 0);
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

    public boolean deletePost(Long postId) {
        Optional<Post> post = findOne(postId);
        if(post.isPresent()) {
            List<PostTagsConnection> connectionList = postTagsConnectionRepository.findByPostId(postId);
            for(PostTagsConnection connection : connectionList) {
                postTagsConnectionRepository.delete(connection.getConnectionId());
            }
            postRepository.delete(postId);

            return true;
        } else {
            return false;
        }
    }



    public List<Post> search(List<Long> tagIdList, int page, Long tripDays) {
        List<Long> connectionList = new ArrayList<>();
        ArrayList<Post> postList = new ArrayList<>();

        if(tagIdList!=null || tripDays!=null) {
            if(tagIdList!=null && tripDays!=null) {
                connectionList = postTagsConnectionRepository.searchWithTagAndDays(tagIdList, tripDays, page);
            } else if(tagIdList!=null && tripDays==null) {
                connectionList = postTagsConnectionRepository.searchWithTag(tagIdList, page);
            } else if(tagIdList==null && tripDays!=null) {
                connectionList = postTagsConnectionRepository.searchWithDays(tripDays, page);
            }

            for(Long postId : connectionList) {
                Optional<Post> postOptional = postRepository.findById(postId);
                if(postOptional.isPresent()) {
                    postList.add(applyTransientData(postOptional.get()));
                }
            }
        } else {
            postList = (ArrayList<Post>) postRepository.getAllPosts(page);
        }

        return postList;
    }

    public int getAllPageSearch(List<Long> tagIdList, Long tripDays) {
        int allPage = 0;

        if(tagIdList!=null && tripDays!=null) {
            allPage = postTagsConnectionRepository.getAllPageSearchWithTagAndDays(tagIdList, tripDays);
        } else if(tagIdList!=null && tripDays==null) {
            allPage = postTagsConnectionRepository.getAllPageSearchWithTag(tagIdList);
        } else if(tagIdList==null && tripDays!=null) {
            allPage = postTagsConnectionRepository.getAllPageSearchWithDays(tripDays);
        } else {
            allPage = postRepository.getAllPageGetAllPosts();
        }

        return allPage;
    }

    public List<Post> searchWithQuery(String query, int page) {
        List<Post> searchedPostList = postRepository.findByQuery(query, page);
        ArrayList<Post> postList = new ArrayList<>();
        for(Post post : searchedPostList) {
            postList.add(applyTransientData(post));
        }
        return postList;
    }

    public int getAllpageSearchWithQuery(String query) {
        return postRepository.getAllPageFindByQuery(query);
    }



}
