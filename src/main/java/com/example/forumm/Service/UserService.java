package com.example.forumm.Service;

import com.example.forumm.Entity.User;
import com.example.forumm.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService{

    @Autowired
    UserRepository userRepo;

    @Override
    public List<User> getAllUsers() {
        return (List<User>) userRepo.findAll();
    }

    @Override
    public User getUser(Long userId) {
        User user= userRepo.findById(userId) .orElse(null) ;
        return user ;
    }

    @Override
    public User addUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepo.deleteById(userId);

    }
}
