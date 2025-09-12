package org.example.tarot_verse_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.tarot_verse_backend.entity.User;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "tarot_readings")
public class TarotReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String question;

    private String cards;

    @Column(length = 2000)
    private String interpretation;

    private LocalDateTime createdAt;
}
