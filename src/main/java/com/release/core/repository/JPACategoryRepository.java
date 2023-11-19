package com.release.core.repository;

import com.release.core.domain.Category;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class JPACategoryRepository implements CategoryRepository {
    private final EntityManager em;

    public JPACategoryRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Category save(Category category) {
        em.persist(category);
        return category;
    }


    @Override
    public Optional<Category> findById(Long categoryId) {
        Category category = em.find(Category.class, categoryId);
        return Optional.ofNullable(category);
    }

    @Override
    public Optional<Category> findByName(String categoryName) {
        return Optional.ofNullable(em.createQuery("select c from Category c where c.categoryName=:categoryName", Category.class)
                .setParameter("categoryName", categoryName)
                .getSingleResult());
    }

}
