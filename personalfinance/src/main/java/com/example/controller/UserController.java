package com.example.controller;

import org.springframework.web.bind.annotation.*;
import com.example.model.User;
import com.example.repository.UserRepository;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepo;

    public UserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userRepo.save(user);
    }

    @GetMapping
    public List<User> getAll() {
        return userRepo.findAll();
    }
}