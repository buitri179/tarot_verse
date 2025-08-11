package org.example.tarot_verse_backend.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.resolver.DefaultAddressResolverGroup;
import org.example.tarot_verse_backend.entity.TarotCard;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.List;
import java.util.Map;

@Component
public class XaiApiClient {
    private final WebClient webClient;

    public XaiApiClient(@Value("${xai.api.key}") String apiKey) {
        HttpClient httpClient = HttpClient.create()
                .resolver(DefaultAddressResolverGroup.INSTANCE);

        this.webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl("https://api.x.ai")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey.trim())
                .build();
    }

    // Gọi API X.ai
    public Map<String, Object> sendMessage(String prompt) {
        Map<String, Object> body = Map.of(
                "model", "grok-beta",
                "messages", List.of(
                        Map.of("role", "user", "content", prompt)
                )
        );

        return webClient.post()
                .uri("/v1/messages")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }

    // Hàm hỏi Tarot Expert, trả về text content
    public String askTarotExpert(String prompt) {
        Map<String, Object> response = sendMessage(prompt);

        if (response == null || !response.containsKey("choices")) {
            throw new RuntimeException("Không nhận được phản hồi hợp lệ từ X.ai");
        }

        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
        if (choices.isEmpty()) {
            throw new RuntimeException("Không có dữ liệu trong phản hồi X.ai");
        }

        Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
        String content = (String) message.get("content");

        if (content == null) {
            throw new RuntimeException("Không tìm thấy nội dung trả về từ assistant");
        }

        // Chỉ lấy phần JSON thuần nếu có
        int startIndex = content.indexOf("[");
        int endIndex = content.lastIndexOf("]");
        if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
            content = content.substring(startIndex, endIndex + 1);
        }

        return content.trim();
    }

    // Lấy JSON deck 78 lá
    public List<TarotCard> askTarotExpertJson(String prompt) {
        try {
            String content = askTarotExpert(prompt);
            return parseDeckJson(content);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi gọi API hoặc parse JSON từ X.ai", e);
        }
    }

    // Parse JSON deck
    public static List<TarotCard> parseDeckJson(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, new TypeReference<>() {});
    }
}
