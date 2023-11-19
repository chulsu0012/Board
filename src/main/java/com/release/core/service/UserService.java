package com.release.core.service;

import com.release.core.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByName(String name);
    List<User> findAll();
    /*
    User createUser(User user);
    */
    User updateUser(User user);
    void deleteUser(User user);

    List<User> findAdminUsers();
    List<User> findUsersWithPagination(int page, int pageSize);

    Long join(User user);
    void validateDuplicateUser(User user);

    String login(String username, String password);

    /*
    User updateUser(Long userId, User updatedUser);

    void deleteUser(Long userId);
     */
}
