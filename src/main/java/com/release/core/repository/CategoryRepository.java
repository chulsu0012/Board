package com.release.core.repository;

import com.release.core.domain.Category;

import java.util.Optional;

public interface CategoryRepository {

    Category save(Category category);

    Optional<Category> findById(Long categoryId);
    Optional<Category> findByName(String categoryName);

}
