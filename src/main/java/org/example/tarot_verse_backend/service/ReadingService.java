package org.example.tarot_verse_backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.tarot_verse_backend.client.XaiApiClient;
import org.example.tarot_verse_backend.dto.TarotReadingResponse; // Import DTO mới
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadingService {
    private final XaiApiClient xaiApiClient;
    private final ObjectMapper objectMapper; // Thêm ObjectMapper để parse JSON

    public TarotReadingResponse getTarotReading(String name, String birthDate, String birthTime,
                                                String gender, String topic, List<String> cards) {

        // Cấu hình lại prompt để yêu cầu AI trả về JSON
        String prompt = String.format(
                "Đóng vai 1 thầy bói tarot chuyên nghiệp. Hãy diễn giải lá bài tarot dựa trên thông tin sau và trả về một JSON object, " +
                        "yêu cầu về cách diễn giải phải giới thiệu về các lá bài được bốc trước. " +
                        "Định dạng JSON phải có hai trường: 'reading' (chuỗi nội dung diễn giải) và 'cards' (mảng chuỗi tên các lá bài). " +
                        "Thông tin: Tên: %s, Ngày sinh: %s, Giờ sinh: %s, Giới tính: %s, Chủ đề: %s, Các lá bài: %s. ",
                name, birthDate, birthTime, gender, topic, String.join(", ", cards)
        );

        try {
            // Lấy kết quả dưới dạng chuỗi JSON từ AI
            String jsonResponse = xaiApiClient.askTarotExpert(prompt);

            // Log kết quả trả về để dễ debug
            System.out.println("JSON response from AI: " + jsonResponse);

            // Parse chuỗi JSON thành đối tượng TarotReadingResponse
            return objectMapper.readValue(jsonResponse, TarotReadingResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // Nếu AI không trả về JSON hợp lệ, bạn có thể tạo một response lỗi
            TarotReadingResponse errorResponse = new TarotReadingResponse();
            errorResponse.setReading("Lỗi khi xử lý kết quả từ AI: " + e.getMessage());
            errorResponse.setCards(List.of());
            return errorResponse;
        } catch (Exception e) {
            e.printStackTrace();
            TarotReadingResponse errorResponse = new TarotReadingResponse();
            errorResponse.setReading("Lỗi khi gọi API X.ai: " + e.getMessage());
            errorResponse.setCards(List.of());
            return errorResponse;
        }
    }
}