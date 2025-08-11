package org.example.tarot_verse_backend.service;

import lombok.RequiredArgsConstructor;
import org.example.tarot_verse_backend.client.XaiApiClient;
import org.example.tarot_verse_backend.entity.TarotCard;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TarotDeckService {
    private final XaiApiClient xaiApiClient;

    public List<TarotCard> getFullDeck() {
        String prompt = """
            Trả về dữ liệu JSON của toàn bộ 78 lá bài Tarot Rider–Waite.
            Mỗi lá gồm:
            - name: tên lá bài (tiếng Anh)
            - uprightMeaning: ý nghĩa xuôi (tiếng Việt)
            - reversedMeaning: ý nghĩa ngược (tiếng Việt)
            - description: mô tả ngắn gọn (tiếng Việt)
            Không giải thích thêm, chỉ trả JSON thuần túy.
        """;

        return xaiApiClient.askTarotExpertJson(prompt);
    }
}
