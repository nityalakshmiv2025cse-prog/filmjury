package com.example.flimjury.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ScoreItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "score_card_id")
    @JsonIgnore
    private ScoreCard scoreCard;

    @ManyToOne(optional = false)
    private Criterion criterion;

    private int score;
}
