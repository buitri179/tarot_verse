package org.example.tarot_verse_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class UserQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String birthDate;
    private String birthTime;
    private String gender;
    private String topic;
    private String question;

    private LocalDateTime createdAt;

    @Column(columnDefinition = "TEXT")
    private String response;
}
