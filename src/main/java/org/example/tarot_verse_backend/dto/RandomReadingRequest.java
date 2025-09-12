package org.example.tarot_verse_backend.dto;

import lombok.Data;

@Data
public class RandomReadingRequest {
    private String name;
    private String birthDate;
    private String birthTime;
    private String gender;
    private String topic;
}
