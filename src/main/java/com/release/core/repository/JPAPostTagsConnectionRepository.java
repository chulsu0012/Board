package com.release.core.repository;

import com.release.core.AppConfig;
import com.release.core.domain.Post;
import com.release.core.domain.PostTagsConnection;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JPAPostTagsConnectionRepository implements PostTagsConnectionRepository{
    private final EntityManager em;

    public static final int PAGE_POST_NUM = 30;

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

    @Override
    public Optional<PostTagsConnection> findByPostIdAndTagId(Long postId, Long tagId) {
        return Optional.ofNullable((PostTagsConnection) em.createQuery("select c from PostTagsConnection c where c.postId=:postId and c.tagId=:tagId")
                .setParameter("postId", postId)
                .setParameter("tagId", tagId)
                .getSingleResult());
    }

    @Override
    public boolean delete(Long connectionId) {
        Optional<PostTagsConnection> postTagsConnection = findById(connectionId);
        if(postTagsConnection.isPresent()) {
            em.remove(postTagsConnection.get());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Long> searchWithTagAndDays(List<Long> tagIdList, Long tripDays, int page) {
        String tagIdListStr = "(" + tagIdList.get(0);
        for(Long tagId : tagIdList.stream().skip(1).toList()) {
            tagIdListStr += ",";
            tagIdListStr += tagId;
        }
        tagIdListStr += ")";

        return em.createQuery("SELECT DISTINCT(c.postId) FROM PostTagsConnection c where c.tagId in :tagIdList and c.postId in (select p.postId from Post p where p.postTripDays=:tripDays)", Long.class)
                .setParameter("tagIdList", tagIdList)
                .setParameter("tripDays", tripDays)
                .setFirstResult(PAGE_POST_NUM * (int) (page-1))
                .setMaxResults(PAGE_POST_NUM)
                .getResultList();
    }

    @Override
    public List<Long> searchWithTag(List<Long> tagIdList, int page) {
        String tagIdListStr = "(" + tagIdList.get(0);
        for(Long tagId : tagIdList.stream().skip(1).toList()) {
            tagIdListStr += ",";
            tagIdListStr += tagId;
        }
        tagIdListStr += ")";

        return em.createQuery("SELECT DISTINCT(c.postId) FROM PostTagsConnection c where c.tagId in :tagIdList", Long.class)
                .setParameter("tagIdList", tagIdList)
                .setFirstResult(PAGE_POST_NUM * (page-1))
                .setMaxResults(PAGE_POST_NUM)
                .getResultList();
    }

    @Override
    public List<Long> searchWithDays(Long tripDays, int page) {
        return em.createQuery("SELECT DISTINCT(c.postId) FROM PostTagsConnection c where c.postId in (select p.postId from Post p where p.postTripDays=:tripDays)", Long.class)
                .setParameter("tripDays", tripDays)
                .setFirstResult(PAGE_POST_NUM * (page-1))
                .setMaxResults(PAGE_POST_NUM)
                .getResultList();
    }
}
