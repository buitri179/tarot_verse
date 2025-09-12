package org.example.tarot_verse_backend.repository;

import org.example.tarot_verse_backend.entity.TarotCard;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TarotCardRepository extends JpaRepository<TarotCard, Long> {
    @Query(value = "SELECT * FROM tarot_cards ORDER BY RAND() LIMIT :count", nativeQuery = true)
    List<TarotCard> findRandomCards(@Param("count") int count);
}
