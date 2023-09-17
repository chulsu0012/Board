package com.release.core.user.service;

import com.release.core.user.model.User;
import com.release.core.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

    User createUser(User user) {
        return null;
    }

    User updateUser(User user) {
        return null;
    }

    void deleteUser(int userId) {

    }

    User getUserById(int userId) {
        return null;
    }

    List<User> getAllUsers() {
        return null;
    }

}
