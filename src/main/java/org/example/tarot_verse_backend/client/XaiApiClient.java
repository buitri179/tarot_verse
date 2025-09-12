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
                "model", "grok-4-0709",
                "messages", List.of(
                        Map.of("role", "user", "content", prompt)
                ),
                "max_tokens", 32768
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

        System.out.println("=== RESPONSE từ X.ai ===");
        System.out.println(response);

        if (response == null || !response.containsKey("content")) {
            throw new RuntimeException("Không nhận được phản hồi hợp lệ từ X.ai: " + response);
        }

        Object contentObj = response.get("content");
        if (!(contentObj instanceof List<?>)) {
            throw new RuntimeException("'content' không phải là một List! Thực tế: " + contentObj);
        }

        List<?> contentList = (List<?>) contentObj;
        if (contentList.isEmpty()) {
            throw new RuntimeException("Không có dữ liệu trong trường 'content'!");
        }

        StringBuilder sb = new StringBuilder();
        int idx = 0;
        for (Object itemObj : contentList) {
            System.out.println("---- Item #" + idx + ": " + itemObj);
            idx++;
            if (itemObj instanceof Map) {
                Map<?, ?> item = (Map<?, ?>) itemObj;
                if ("text".equals(item.get("type")) && item.get("text") != null) {
                    sb.append(item.get("text"));
                }
            }
        }

        String content = sb.toString();
        if (content.isEmpty()) {
            throw new RuntimeException("Không tìm thấy nội dung text trong content từ assistant!");
        }

        System.out.println("=== Nội dung text trả về từ X.ai: ===");
        System.out.println(content);

        return content.trim();
    }

    // Lấy JSON deck 78 lá
    public List<TarotCard> askTarotExpertJson(String prompt) {
        try {
            String content = askTarotExpert(prompt);

            // Kiểm tra xem có đúng là mảng JSON không
            if (!content.startsWith("[") || !content.endsWith("]")) {
                throw new RuntimeException(
                        "Kết quả trả về không phải là mảng JSON! Nội dung: " + content
                );
            }

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