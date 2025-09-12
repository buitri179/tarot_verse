package org.example.tarot_verse_backend.service;

import lombok.RequiredArgsConstructor;
import org.example.tarot_verse_backend.entity.TarotCard;
import org.example.tarot_verse_backend.repository.TarotCardRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TarotDeckService {
    private final TarotCardRepository tarotCardRepository;

    // This is the main method to be used by the controller
    public List<TarotCard> getFullDeck() {
        return tarotCardRepository.findAll();
    }

    public List<TarotCard> getRandomCards(int count) {
        List<TarotCard> allCards = tarotCardRepository.findAll();
        Collections.shuffle(allCards); // xáo bộ bài
        return allCards.subList(0, Math.min(count, allCards.size()));
    }
}
