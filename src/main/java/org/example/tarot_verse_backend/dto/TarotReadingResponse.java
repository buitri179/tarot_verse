package org.example.tarot_verse_backend.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class TarotReadingResponse {
    private Map<String, String> cards_detail;
    private List<String> cards;
    private String conclusion;
}
