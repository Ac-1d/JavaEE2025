package com.example.library.service;

import com.example.library.entity.User;
import com.example.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        if (user.getRole() == null) {
            user.setRole("USER");  // 默认角色
        }
        return userRepository.save(user);
    }

    // 登录验证（明文密码比对）
    public User login(String username, String rawPassword) {
        User user = userRepository.findByUsername(username);
        if (user != null && rawPassword.equals(user.getPassword())) {
            return user;
        }
        return null;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}