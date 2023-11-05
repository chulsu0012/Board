package com.release.core.bookmark.repository;

import java.util.List;
import java.util.Optional;

import com.release.core.bookmark.domain.Bookmark;
import jakarta.persistence.EntityManager;

public class BookmarkRepositoryImpl implements BookmarkRepository {

    private final EntityManager em;

    public BookmarkRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Bookmark save(Bookmark bookmark) {
        em.persist(bookmark);
        return bookmark;
    }

    @Override
    public void delete(Long bookmarkId) {
        Bookmark bookmark = em.find(Bookmark.class, bookmarkId);
        em.remove(bookmark);
    }

    @Override
    public Optional<Bookmark> findByBookmarkId(Long bookmarkId) {
        Bookmark bookmark = em.find(Bookmark.class, bookmarkId);
        return Optional.ofNullable(bookmark);
    }

    @Override
    public Optional<Bookmark> findByPostId(Long postId) {
        return em.createQuery("select b from Bookmark b where b.postId = :postId", Bookmark.class).setParameter("postId", postId)
            .getResultList().stream().findAny();
    }

    @Override
    public List<Bookmark> findAll(Long userId) {
        return em.createQuery("select b from Bookmark b where b.userId = :userId", Bookmark.class).setParameter("userId", userId)
            .getResultList();
    }
    
}
