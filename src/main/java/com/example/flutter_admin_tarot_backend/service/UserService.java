package com.example.flutter_admin_tarot_backend.service;

import com.example.flutter_admin_tarot_backend.entity.User;
import com.example.flutter_admin_tarot_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public List<User> getAllUsers() {
        return repo.findAll();
    }

    public User getUserById(Long id) {
        Optional<User> user = repo.findById(id);
        return user.orElse(null);
    }

    public void deleteUser(Long id) {
        repo.deleteById(id);
    }
}