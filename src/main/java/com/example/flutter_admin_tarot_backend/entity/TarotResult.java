package com.example.flutter_admin_tarot_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tarot_results")
public class TarotResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    @ElementCollection
    @CollectionTable(name = "tarot_cards", joinColumns = @JoinColumn(name = "tarot_result_id"))
    @Column(name = "card")
    private java.util.List<String> cards;

    private String detailCard1;
    private String detailCard2;
    private String detailCard3;
    private String summary;
}
