package com.release.core.repository;

import com.release.core.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;

public class MemoryUserRepository implements UserRepository{
    private static Map<Long, User> store = new HashMap();
    private static long sequence = 0L;


    @Override
    public User save(User user) {
        user.setId(++sequence);
        store.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<User> findByName(String name) {
        return store.values().stream()
                .filter(user -> user.getName().equals(name))
                .findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void delete(User user) {
        return;
    }

    @Override
    public User update(User user) {
        return user;
    }

    @Override
    public List<User> findAdminUsers() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<User> findUsersWithPagination(int page, int pageSize) {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
