package com.example.flimjury.controller;

import com.example.flimjury.dto.LeaderboardItem;
import com.example.flimjury.service.ScoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
public class LeaderboardController {
    private final ScoreService scoreService;

    public LeaderboardController(ScoreService scoreService) { this.scoreService = scoreService; }

    @GetMapping
    public List<LeaderboardItem> leaderboard() { return scoreService.getLeaderboard(); }
}
