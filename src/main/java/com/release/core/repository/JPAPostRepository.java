package com.release.core.repository;

import com.release.core.domain.Post;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JPAPostRepository implements PostRepository {

    private final EntityManager em;

    public JPAPostRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Post save(Post post) {
        em.persist(post);
        return null;
    }

    @Override
    public Optional<Post> findById(Long postId) {
        Post post = em.find(Post.class, postId);
        return Optional.ofNullable(post);
    }

    @Override
    public List<Post> findByWriterUserId(Long writerUserId) {
        return em.createQuery("select p from Post p where p.writerUserId=:userId", Post.class)
                .setParameter("userId", writerUserId)
                .getResultList();
    }

    @Override
    public List<Post> findByTripDays(Long tripDays) {
        return em.createQuery("select p from Post p where p.postTripDays=:trip_days", Post.class)
                .setParameter("trip_days", tripDays)
                .getResultList();
    }

    @Override
    public List<Post> getAllPosts() {
        return em.createQuery("select p from Post p", Post.class).getResultList();
    }

    @Override
    public List<Post> findByPostDate(String postData) {
        return em.createQuery("select p from Post p where p.postDate=:post_date", Post.class)
                .setParameter("post_date", postData)
                .getResultList();
    }
}
