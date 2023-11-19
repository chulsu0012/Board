package com.release.core.repository;

import com.release.core.domain.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository {

    Tag save(Tag tag);

    Optional<Tag> findById(Long tagId);
    Optional<Tag> findByName(String tagName);
    List<Tag> findByParentCategory(Long categoryId);
}
