package com.example.forumm.Service;

import com.example.forumm.Entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getAllUsers();
    User getUser(Long userId);
    User addUser(User user);
    void deleteUser(Long userId);
}
