package org.example.tarot_verse_backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.tarot_verse_backend.service.ReadingService;
import org.example.tarot_verse_backend.service.TarotDeckService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tarot")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TarotController {
    private final TarotDeckService tarotDeckService;
    private final ReadingService readingService;

    @GetMapping("/deck")
    public Object getDeck() {
        try {
            // Đảm bảo TarotDeckService.getFullDeck() đã gọi XaiApiClient bên trong
            return Map.of("deck", tarotDeckService.getFullDeck());
        } catch (Exception ex) {
            return Map.of("error", "Không lấy được bộ bài Tarot: " + ex.getMessage());
        }
    }

    @PostMapping("/reading")
    public Object getReading(@RequestBody Map<String, Object> req) {
        try {
            String result = readingService.getTarotReading(
                    (String) req.get("name"),
                    (String) req.get("birthDate"),
                    (String) req.get("birthTime"),
                    (String) req.get("gender"),
                    (String) req.get("topic"),
                    (String) req.get("question"),
                    (List<String>) req.get("cards")
            );
            return Map.of("result", result);
        } catch (Exception ex) {
            return Map.of("error", "Không lấy được kết quả reading: " + ex.getMessage());
        }
    }
}