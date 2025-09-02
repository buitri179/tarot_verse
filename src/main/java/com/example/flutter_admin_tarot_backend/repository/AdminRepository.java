package com.example.flutter_admin_tarot_backend.repository;


import com.example.flutter_admin_tarot_backend.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByUsername(String username);
}