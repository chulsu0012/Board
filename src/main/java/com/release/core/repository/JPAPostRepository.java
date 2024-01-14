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
    public boolean delete(Long postId) {
        Optional<Post> post = findById(postId);
        if(post.isPresent()) {
            em.remove(post.get());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Post> findByWriterUserId(Long writerUserId, int page) {
        return em.createQuery("select p from Post p where p.writerUserId=:userId", Post.class)
                .setParameter("userId", writerUserId)
                .setFirstResult(PAGE_POST_NUM * (page-1))
                .setMaxResults(PAGE_POST_NUM)
                .getResultList();
    }

    @Override
    public int getAllPageFindByWriterUserId(Long writerUserId) {
        double size = em.createQuery("select p from Post p where p.writerUserId=:userId", Post.class)
                .setParameter("userId", writerUserId)
                .getResultList().size();

        return (int) Math.floor(size/PAGE_POST_NUM)+1;
    }


    @Override
    public List<Post> findByTripDays(Long tripDays, int page) {
        return em.createQuery("select p from Post p where p.postTripDays=:trip_days", Post.class)
                .setParameter("trip_days", tripDays)
                .setFirstResult(PAGE_POST_NUM * (page-1))
                .setMaxResults(PAGE_POST_NUM)
                .getResultList();
    }

    @Override
    public int getAllPageFindByTripDays(Long tripDays) {
        double size = em.createQuery("select p from Post p where p.postTripDays=:trip_days", Post.class)
                .setParameter("trip_days", tripDays)
                .getResultList().size();


        return size==0?0:(int) Math.floor(size/PAGE_POST_NUM)+1;
    }


    @Override
    public List<Post> getAllPosts(int page) {
        return em.createQuery("select p from Post p", Post.class)
                .setFirstResult(PAGE_POST_NUM * (page-1))
                .setMaxResults(PAGE_POST_NUM)
                .getResultList();
    }

    @Override
    public int getAllPageGetAllPosts() {
        double size = em.createQuery("select p from Post p", Post.class)
                .getResultList().size();

        return size==0?0:(int) Math.floor(size/PAGE_POST_NUM)+1;
    }

    @Override
    public List<Post> findByPostDate(String postData, int page) {
        return em.createQuery("select p from Post p where p.postDate=:post_date", Post.class)
                .setParameter("post_date", postData)
                .setFirstResult(PAGE_POST_NUM * (page-1))
                .setMaxResults(PAGE_POST_NUM)
                .getResultList();
    }

    @Override
    public int getAllPageFindByPostDate(String postData) {
        double size = em.createQuery("select p from Post p where p.postDate=:post_date", Post.class)
                .setParameter("post_date", postData)
                .getResultList().size();

        return size==0?0:(int) Math.floor(size/PAGE_POST_NUM)+1;
    }

    @Override
    public List<Post> findByQuery(String query, int page) {
        String queryForJPQL = query.replaceAll(" ", "%");

        return em.createQuery("select p from Post p where p.postTitle like concat('%', :keyword, '%') or p.postContent like concat('%', :keyword, '%')", Post.class)
                .setParameter("keyword", queryForJPQL)
                .setFirstResult(PAGE_POST_NUM * (page-1))
                .setMaxResults(PAGE_POST_NUM)
                .getResultList();
    }

    @Override
    public int getAllPageFindByQuery(String query) {
        String queryForJPQL = query.replaceAll(" ", "%");

        double size = em.createQuery("select p from Post p where p.postTitle like concat('%', :keyword, '%') or p.postContent like concat('%', :keyword, '%')", Post.class)
                .setParameter("keyword", queryForJPQL)
                .getResultList().size();

        return size==0?0:(int) Math.floor(size/PAGE_POST_NUM)+1;
    }
}
