package com.release.core.repository;

import com.release.core.domain.User;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JPAUserRepository implements UserRepository{

    private final EntityManager em;

    public JPAUserRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public User save(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = em.find(User.class, id);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByName(String name) {
        List<User> result = em.createQuery("select u from User u where u.userName = :name", User.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }
    @Override
    public void delete(User user) {
        em.remove(user);
    }
    @Override
    public User update(User user) {
        return em.merge(user);
    }

    // 추가적인 사용자 관리 기능
    @Override
    public List<User> findAdminUsers() {
        return em.createQuery("select u from User u where u.UserIsAdmin = 1", User.class)
                .getResultList();
    }

    @Override
    public List<User> findUsersWithPagination(int page, int pageSize) {
        return em.createQuery("select u from User u", User.class)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

}
