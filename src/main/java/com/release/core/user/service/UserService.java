package com.release.core.user.service;

import com.release.core.user.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User updateUser(User user);
    void deleteUser(int userId);
    User getUserById(int userId);
    List<User> getAllUsers();
}
