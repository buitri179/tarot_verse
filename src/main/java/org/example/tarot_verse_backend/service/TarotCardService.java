package org.example.tarot_verse_backend.service;

import org.example.tarot_verse_backend.entity.TarotCard;
import org.example.tarot_verse_backend.repository.TarotCardRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TarotCardService {
    private final TarotCardRepository repository;

    public TarotCardService(TarotCardRepository repository) {
        this.repository = repository;
    }

    public List<TarotCard> getAllCards() {
        return repository.findAll();
    }

    public List<TarotCard> getRandomCards(int count) {
        List<TarotCard> allCards = repository.findAll();
        Collections.shuffle(allCards);
        return allCards.subList(0, Math.min(count, allCards.size()));
    }
}
