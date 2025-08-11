package org.example.tarot_verse_backend.service;

import lombok.RequiredArgsConstructor;
import org.example.tarot_verse_backend.client.XaiApiClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadingService {
    private final XaiApiClient xaiApiClient;

    public String getTarotReading(String name, String birthDate, String birthTime,
                                  String gender, String topic, String question, List<String> cards) {

        String prompt = String.format(
                "Bạn là thầy bói tarot chuyên nghiệp. Dựa trên thông tin: " +
                        "Tên: %s, Ngày sinh: %s, Giờ sinh: %s, Giới tính: %s, Chủ đề: %s, Các lá bài: %s. " +
                        "Câu hỏi: %s",
                name, birthDate, birthTime, gender, topic, String.join(", ", cards), question
        );

        try {
            // Gọi trực tiếp hàm askTarotExpert đã chuẩn hóa API X.ai
            return xaiApiClient.askTarotExpert(prompt);
        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi khi gọi API X.ai: " + e.getMessage();
        }
    }
}
