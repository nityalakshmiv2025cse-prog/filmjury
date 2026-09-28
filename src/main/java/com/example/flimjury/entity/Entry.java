package com.example.flimjury.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String genre;
    private String videoLink;
    private String teamName;

    @ManyToMany
    @JoinTable(name = "entry_judge",
        joinColumns = @JoinColumn(name = "entry_id"),
        inverseJoinColumns = @JoinColumn(name = "judge_id"))
    private Set<Judge> assignedJudges = new HashSet<>();
}
