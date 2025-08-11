package org.example.tarot_verse_backend.repository;

import org.example.tarot_verse_backend.entity.TarotCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarotCardRepository extends JpaRepository<TarotCard, Long> {
}