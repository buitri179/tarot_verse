package org.example.tarot_verse_backend.repository;

import org.example.tarot_verse_backend.entity.TarotReading;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarotReadingRepository extends JpaRepository<TarotReading, Long> {}
