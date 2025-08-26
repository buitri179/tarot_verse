package org.example.tarot_verse_backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.tarot_verse_backend.dto.TarotReadingResponse; // Thêm import này
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
            return Map.of("deck", tarotDeckService.getFullDeck());
        } catch (Exception ex) {
            return Map.of("error", "Không lấy được bộ bài Tarot: " + ex.getMessage());
        }
    }

    @PostMapping("/reading")
    public Object getReading(@RequestBody Map<String, Object> req) {
        try {
            // Thay vì gọi và lưu kết quả dưới dạng String, hãy lưu dưới dạng đối tượng
            TarotReadingResponse response = readingService.getTarotReading(
                    (String) req.get("name"),
                    (String) req.get("birthDate"),
                    (String) req.get("birthTime"),
                    (String) req.get("gender"),
                    (String) req.get("topic"),
                    (List<String>) req.get("cards")
            );

            // Trả về trực tiếp đối tượng TarotReadingResponse
            return response;

        } catch (Exception ex) {
            // Trường hợp lỗi, trả về một đối tượng lỗi để frontend dễ xử lý
            return Map.of("error", "Không lấy được kết quả reading: " + ex.getMessage());
        }
    }
}