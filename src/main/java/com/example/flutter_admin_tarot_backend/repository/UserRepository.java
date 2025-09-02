package com.example.flutter_admin_tarot_backend.repository;

import com.example.flutter_admin_tarot_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}