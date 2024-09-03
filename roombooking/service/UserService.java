package com.example.roombooking.service;

import com.example.roombooking.model.User;
import com.example.roombooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private static UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        UserService.userRepository = userRepository;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public static User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public boolean authenticate(String email, String password) {
        User user = userRepository.findByEmail(email);
        return user != null && user.getPassword().equals(password);
    }
    public List<User> findAll() {
        return userRepository.findAll();
    }
    public static User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}