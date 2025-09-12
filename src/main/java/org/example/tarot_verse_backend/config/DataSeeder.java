package org.example.tarot_verse_backend.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tarot_verse_backend.entity.TarotCard;
import org.example.tarot_verse_backend.repository.TarotCardRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final TarotCardRepository tarotCardRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {
        if (tarotCardRepository.count() == 0) {
            log.info("Database is empty. Seeding Tarot cards from local tarot_cards.json...");
            try (InputStream inputStream = new ClassPathResource("tarot_cards.json").getInputStream()) {
                List<TarotCard> deck = objectMapper.readValue(inputStream, new TypeReference<List<TarotCard>>() {});
                tarotCardRepository.saveAll(deck);
                log.info("Successfully seeded {} Tarot cards.", deck.size());
            } catch (Exception e) {
                log.error("Failed to read or seed Tarot cards from local JSON.", e);
            }
        } else {
            log.info("Database already contains Tarot cards. Skipping seed.");
        }
    }
}
