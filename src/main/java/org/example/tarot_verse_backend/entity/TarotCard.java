package org.example.tarot_verse_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tarot_cards")
@Data
public class TarotCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 2000)
    private String uprightMeaning;

    @Column(length = 2000)
    private String reversedMeaning;

    @Column(length = 2000)
    private String description;

    private String imageUrl;
}
