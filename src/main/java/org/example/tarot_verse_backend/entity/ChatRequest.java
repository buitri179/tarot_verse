package org.example.tarot_verse_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRequest {
    private String model;
    private List<Message> messages;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Message {
        private String role;     // "system" | "user"
        private String content;
    }

    public static ChatRequest fromPrompt(String prompt) {
        return new ChatRequest(
                "grok-beta", // Hoặc model bạn đang dùng của X.AI
                List.of(
                        new Message("system", "Bạn là chuyên gia bói bài Tarot."),
                        new Message("user", prompt)
                )
        );
    }
}