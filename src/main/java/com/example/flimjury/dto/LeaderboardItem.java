package com.example.flimjury.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LeaderboardItem {
    private Long entryId;
    private String title;
    private String teamName;
    private double averageScore;
    private int rank;
}
