package com.example.flutter_admin_tarot_backend.repository;


import com.example.flutter_admin_tarot_backend.entity.TarotResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarotResultRepository extends JpaRepository<TarotResult, Long> {}