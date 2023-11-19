package com.release.core.repository;

import com.release.core.domain.Tag;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JPATagRepository implements TagRepository {
    private final EntityManager em;

    public JPATagRepository(EntityManager em) {
        this.em = em;
    }


    @Override
    public Tag save(Tag tag) {
        em.persist(tag);
        return tag;
    }

    @Override
    public Optional<Tag> findById(Long tagId) {
        Tag tag = em.find(Tag.class, tagId);
        return Optional.ofNullable(tag);
    }

    @Override
    public Optional<Tag> findByName(String tagName) {
        return Optional.ofNullable(em.createQuery("select t from Tag t where t.tagName=:tagName", Tag.class)
                .setParameter("tagName", tagName)
                .getSingleResult());
    }

    @Override
    public List<Tag> findByParentCategory(Long categoryId) {
        return em.createQuery("select t from Tag t where t.tagParentId=:parentId", Tag.class)
                .setParameter("parentId", categoryId)
                .getResultList();
    }
}
