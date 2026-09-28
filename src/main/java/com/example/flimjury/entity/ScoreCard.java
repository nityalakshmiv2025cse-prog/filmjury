package com.example.flimjury.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"judge_id", "entry_id"}))
public class ScoreCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "judge_id")
    private Judge judge;

    @ManyToOne(optional = false)
    @JoinColumn(name = "entry_id")
    private Entry entry;

    @OneToMany(mappedBy = "scoreCard", cascade = CascadeType.ALL)
    private List<ScoreItem> items = new ArrayList<>();

    private double totalScore;
}
