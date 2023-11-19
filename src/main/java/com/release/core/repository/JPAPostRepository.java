package com.release.core.repository;

import com.release.core.domain.Post;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JPAPostRepository implements PostRepository {

    private static final int PAGE_POST_NUM = 30;

    private final EntityManager em;

    public JPAPostRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Post save(Post post) {
        em.persist(post);
        return post;
    }

    @Override
    public Optional<Post> findById(Long postId) {
        Post post = em.find(Post.class, postId);
        return Optional.ofNullable(post);
    }

    @Override
    public List<Post> findByWriterUserId(Long writerUserId, int start) {
        return em.createQuery("select p from Post p where p.writerUserId=:userId", Post.class)
                .setParameter("userId", writerUserId)
                .setFirstResult(start)
                .setMaxResults(PAGE_POST_NUM)
                .getResultList();
    }

    @Override
    public List<Post> findByTripDays(Long tripDays, int start) {
        return em.createQuery("select p from Post p where p.postTripDays=:trip_days", Post.class)
                .setParameter("trip_days", tripDays)
                .setFirstResult(start)
                .setMaxResults(PAGE_POST_NUM)
                .getResultList();
    }

    @Override
    public List<Post> getAllPosts(int start) {
        return em.createQuery("select p from Post p", Post.class)
                .setFirstResult(start)
                .setMaxResults(PAGE_POST_NUM)
                .getResultList();
    }

    @Override
    public List<Post> findByPostDate(String postData, int start) {
        return em.createQuery("select p from Post p where p.postDate=:post_date", Post.class)
                .setParameter("post_date", postData)
                .setFirstResult(start)
                .setMaxResults(PAGE_POST_NUM)
                .getResultList();
    }

    @Override
    public boolean delete(Long postId) {
        Optional<Post> post = findById(postId);
        if(post.isPresent()) {
            em.remove(post.get());
            return true;
        } else {
            return false;
        }
    }
}
