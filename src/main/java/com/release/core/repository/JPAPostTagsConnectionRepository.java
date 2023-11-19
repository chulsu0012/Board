package com.release.core.repository;

import com.release.core.domain.PostTagsConnection;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JPAPostTagsConnectionRepository implements PostTagsConnectionRepository{
    private final EntityManager em;

    public JPAPostTagsConnectionRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public PostTagsConnection save(PostTagsConnection postTagConnection) {
        em.persist(postTagConnection);
        return postTagConnection;
    }


    @Override
    public Optional<PostTagsConnection> findById(Long connectionId) {
        PostTagsConnection postTagConnection = em.find(PostTagsConnection.class, connectionId);
        return Optional.ofNullable(postTagConnection);
    }

    @Override
    public List<PostTagsConnection> findByPostId(Long postId) {
        return em.createQuery("select c from PostTagsConnection c where c.postId=:postId", PostTagsConnection.class)
                .setParameter("postId", postId)
                .getResultList();
    }

    @Override
    public List<PostTagsConnection> findByTagId(Long tagId) {
        return em.createQuery("select c from PostTagsConnection c where c.tagId=:tagId", PostTagsConnection.class)
                .setParameter("tagId", tagId)
                .getResultList();
    }
}
