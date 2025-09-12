package org.example.tarot_verse_backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.tarot_verse_backend.dto.TarotReadingResponse;
import org.example.tarot_verse_backend.entity.TarotReading;
import org.example.tarot_verse_backend.service.ReadingService;
import org.example.tarot_verse_backend.service.TarotDeckService;
import org.example.tarot_verse_backend.repository.UserRepository;
import org.example.tarot_verse_backend.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tarot")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TarotController {
    private final TarotDeckService tarotDeckService;
    private final ReadingService readingService;
    private final UserRepository userRepository;

    @GetMapping("/deck")
    public Object getDeck() {
        try {
            return Map.of("deck", tarotDeckService.getFullDeck());
        } catch (Exception ex) {
            return Map.of("error", "Không lấy được bộ bài Tarot: " + ex.getMessage());
        }
    }

    @PostMapping("/reading")
    public Object getReading(@RequestBody Map<String, Object> req) {
        try {
            TarotReadingResponse response = readingService.getTarotReading(
                    (String) req.get("name"),
                    (String) req.get("birthDate"),
                    (String) req.get("gender"),
                    (String) req.get("topic"),
                    (List<String>) req.get("cards")
            );
            return response;
        } catch (Exception ex) {
            return Map.of("error", "Không lấy được kết quả reading: " + ex.getMessage());
        }
    }

    @PostMapping("/random-cards")
    public Object getRandomCards() {
        try {
            return Map.of("cards", tarotDeckService.getRandomCards(3));
        } catch (Exception ex) {
            return Map.of("error", "Không random được lá bài: " + ex.getMessage());
        }
    }

    @PostMapping("/readings")
    public ResponseEntity<Void> saveReading(@RequestBody TarotReading reading, Principal principal) {
        if (principal == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User currentUser = userRepository.findByEmail(principal.getName())
                                      .orElseThrow(() -> new RuntimeException("User not found"));
        readingService.saveReading(reading, currentUser.getId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/readings")
    public ResponseEntity<List<TarotReading>> getReadings(Principal principal) {
        if (principal == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User currentUser = userRepository.findByEmail(principal.getName())
                                      .orElseThrow(() -> new RuntimeException("User not found"));
        List<TarotReading> readings = readingService.getReadingsByUserId(currentUser.getId());
        return ResponseEntity.ok(readings);
    }

    @GetMapping("/reading/{readingId}")
    public ResponseEntity<TarotReading> getReadingById(@PathVariable Long readingId) {
        return readingService.getReadingById(readingId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/share/{readingId}")
    public ResponseEntity<String> generateShareLink(@PathVariable Long readingId) {
        // In a real application, you would generate a dynamic link here,
        // possibly involving a short URL service or a deep link configuration.
        // For now, we'll return a placeholder URL.
        String shareableLink = "https://yourdomain.com/share/reading/" + readingId;
        return ResponseEntity.ok(shareableLink);
    }
}
