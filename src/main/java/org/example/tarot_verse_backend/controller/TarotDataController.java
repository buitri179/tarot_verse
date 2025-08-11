package org.example.tarot_verse_backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.tarot_verse_backend.entity.TarotCard;
import org.example.tarot_verse_backend.repository.TarotCardRepository;
import org.example.tarot_verse_backend.client.XaiApiClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tarot")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TarotDataController {

    private final XaiApiClient xaiApiClient;
    private final TarotCardRepository tarotCardRepository;

    @PostMapping("/import-deck")
    public Object importDeck() {
        try {
            String prompt = """
                Trả về dữ liệu JSON của toàn bộ 78 lá bài Tarot Rider–Waite.
                Mỗi lá gồm:
                - name: tên lá bài (tiếng Anh)
                - uprightMeaning: ý nghĩa xuôi (tiếng Việt)
                - reversedMeaning: ý nghĩa ngược (tiếng Việt)
                - description: mô tả ngắn gọn (tiếng Việt)
                Không giải thích thêm, chỉ trả JSON thuần túy.
                """;

            String jsonContent = xaiApiClient.askTarotExpert(prompt);

            // Parse JSON
            List<TarotCard> cards = XaiApiClient.parseDeckJson(jsonContent);

            // Xóa dữ liệu cũ để tránh trùng
            tarotCardRepository.deleteAll();

            // Lưu toàn bộ vào DB
            tarotCardRepository.saveAll(cards);

            return "Đã import " + cards.size() + " lá bài vào MySQL.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi khi import: " + e.getMessage();
        }
    }

    @GetMapping("/test-deck")
    public Object testDeck() {
        try {
            String prompt = """
            Trả về dữ liệu JSON của toàn bộ 78 lá bài Tarot Rider–Waite.
            Mỗi lá gồm:
            - name: tên lá bài (tiếng Anh)
            - uprightMeaning: ý nghĩa xuôi (tiếng Việt)
            - reversedMeaning: ý nghĩa ngược (tiếng Việt)
            - description: mô tả ngắn gọn (tiếng Việt)
            Không giải thích thêm, chỉ trả JSON thuần túy.
            """;

            String jsonContent = xaiApiClient.askTarotExpert(prompt);

            // Parse JSON để kiểm tra
            List<TarotCard> cards = XaiApiClient.parseDeckJson(jsonContent);

            return Map.of(
                    "total", cards.size(),
                    "sample", cards.subList(0, Math.min(3, cards.size())),
                    "rawJson", jsonContent
            );
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("error", e.getMessage());
        }
    }

}
