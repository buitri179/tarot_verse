package org.example.tarot_verse_backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class TarotReadingResponse {
    private String reading;
    private List<String> cards;
}