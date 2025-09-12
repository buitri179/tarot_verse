package org.example.tarot_verse_backend.repository;

import org.example.tarot_verse_backend.entity.TarotReading;
import org.example.tarot_verse_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarotReadingRepository extends JpaRepository<TarotReading, Long> {
    List<TarotReading> findByUserOrderByCreatedAtDesc(User user);
}
