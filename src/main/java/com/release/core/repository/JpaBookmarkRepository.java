package com.release.core.repository;

import java.util.List;
import java.util.Optional;

import com.release.core.domain.Bookmark;

import jakarta.persistence.EntityManager;

public class JPABookmarkRepository implements BookmarkRepository {

    private final EntityManager em;

    public JPABookmarkRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Bookmark saveBookmark(Bookmark bookmark) {
        em.persist(bookmark);
        return bookmark;
    }

    @Override
    public void deleteBookmark(Long bookmarkId) {
        Bookmark bookmark = em.find(Bookmark.class, bookmarkId);
        em.remove(bookmark);
    }

    @Override
    public Optional<Bookmark> findBookmarkByBookmarkId(Long bookmarkId) {
        Bookmark bookmark = em.find(Bookmark.class, bookmarkId);
        return Optional.ofNullable(bookmark);
    }

    @Override
    public Optional<Bookmark> findBookmarkByPostId(Long userId, Long postId) {
        return em.createQuery("select b from Bookmark b where b.userId = :userId and b.postId = :postId",
            Bookmark.class)
            .setParameter("userId", userId).setParameter("postId", postId)
            .getResultList().stream().findAny();
    }

    @Override
    public List<Bookmark> findAllBookmarks(Long userId) {
        return em.createQuery("select b from Bookmark b where b.userId = :userId",
            Bookmark.class).setParameter("userId", userId)
            .getResultList();
    }
    
}
