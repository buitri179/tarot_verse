package org.example.tarot_verse_backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.tarot_verse_backend.client.XaiApiClient;
import org.example.tarot_verse_backend.dto.TarotReadingResponse;
import org.example.tarot_verse_backend.entity.TarotCard;
import org.example.tarot_verse_backend.entity.TarotReading;
import org.example.tarot_verse_backend.repository.TarotCardRepository;
import org.example.tarot_verse_backend.repository.TarotReadingRepository;
import org.example.tarot_verse_backend.repository.UserRepository;
import org.example.tarot_verse_backend.entity.User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReadingService {
    private final XaiApiClient xaiApiClient;
    private final ObjectMapper objectMapper;
    private final TarotCardRepository tarotCardRepository;
    private final TarotReadingRepository tarotReadingRepository;
    private final UserRepository userRepository;

    public TarotReadingResponse getTarotReading(String name, String birthDate,
                                                String gender, String topic, List<String> cards) {
        String prompt = String.format(
                "Bạn là 1 thực thể tâm linh ngoài vũ trụ. Hãy diễn giải lá bài tarot dựa trên thông tin sau và trả về một JSON object có đúng 3 trường sau: " +
                        "1. \"cards_detail\": một JSON object, trong đó mỗi key là tên một lá bài và value là phần diễn giải chi tiết cho lá bài đó. " +
                        "2. \"cards\": một mảng chuỗi chứa tên các lá bài đã bốc. " +
                        "3. \"conclusion\": một chuỗi văn bản đưa ra kết luận chung cho tất cả các lá bài. " +
                        "Thông tin: Tên: %s, Ngày sinh: %s, Giới tính: %s, Chủ đề: %s, Các lá bài: %s. ",
                name, birthDate, gender, topic, String.join(", ", cards)
        );

        try {
            String jsonResponse = xaiApiClient.askTarotExpert(prompt);
            System.out.println("JSON response from AI: " + jsonResponse);
            return objectMapper.readValue(jsonResponse, TarotReadingResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            TarotReadingResponse errorResponse = new TarotReadingResponse();
            errorResponse.setConclusion("Lỗi khi xử lý kết quả từ AI: " + e.getMessage());
            errorResponse.setCards(List.of());
            return errorResponse;
        } catch (Exception e) {
            e.printStackTrace();
            TarotReadingResponse errorResponse = new TarotReadingResponse();
            errorResponse.setConclusion("Lỗi khi gọi API X.ai: " + e.getMessage());
            errorResponse.setCards(List.of());
            return errorResponse;
        }
    }

    public void saveReading(TarotReading reading, Long userId) {
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        reading.setUser(user);
        tarotReadingRepository.save(reading);
    }

    public List<TarotReading> getReadingsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        return tarotReadingRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public Optional<TarotReading> getReadingById(Long id) {
        return tarotReadingRepository.findById(id);
    }
}
