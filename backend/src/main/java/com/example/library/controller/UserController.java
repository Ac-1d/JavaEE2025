package com.example.library.controller;

import com.example.library.entity.User;
import com.example.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    // 注册接口
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userService.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.badRequest().body("用户名已存在");
        }
        User savedUser = userService.registerUser(user);
        savedUser.setPassword(null);  // 不返回密码
        return ResponseEntity.ok(savedUser);
    }

    // 登录接口
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User loggedUser = userService.login(user.getUsername(), user.getPassword());
        if (loggedUser == null) {
            return ResponseEntity.status(401).body("用户名或密码错误");
        }
        loggedUser.setPassword(null);  // 不返回密码
        return ResponseEntity.ok(loggedUser);
    }

    // 通过用户名获取用户
    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }
}
