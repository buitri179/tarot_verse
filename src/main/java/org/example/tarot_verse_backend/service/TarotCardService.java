package org.example.tarot_verse_backend.service;

import org.example.tarot_verse_backend.client.XaiApiClient;
import org.example.tarot_verse_backend.entity.TarotCard;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TarotCardService {

    private final XaiApiClient xaiApiClient;

    public TarotCardService(XaiApiClient xaiApiClient) {
        this.xaiApiClient = xaiApiClient;
    }

    public List<TarotCard> getAllCards() {
        String prompt = "Chỉ trả về đúng một mảng JSON gồm 78 lá bài Tarot, mỗi phần tử có name, uprightMeaning, reversedMeaning, description. Không trả về văn bản giải thích nào ngoài mảng JSON.";
        return xaiApiClient.askTarotExpertJson(prompt);
    }

    public List<TarotCard> getRandomCards(int count) {
        List<TarotCard> allCards = getAllCards();
        Collections.shuffle(allCards);
        return allCards.subList(0, Math.min(count, allCards.size()));
    }
}