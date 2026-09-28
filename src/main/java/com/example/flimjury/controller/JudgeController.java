package com.example.flimjury.controller;

import com.example.flimjury.dto.AssignRequest;
import com.example.flimjury.dto.JudgeRequest;
import com.example.flimjury.entity.Judge;
import com.example.flimjury.service.JudgeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/judges")
public class JudgeController {
    private final JudgeService judgeService;

    public JudgeController(JudgeService judgeService) { this.judgeService = judgeService; }

    @PostMapping
    public ResponseEntity<Judge> create(@Valid @RequestBody JudgeRequest req) {
        return ResponseEntity.status(201).body(judgeService.createJudge(req));
    }

    @GetMapping
    public List<Judge> getAll() { return judgeService.getAll(); }

    @PostMapping("/{judgeId}/assignments")
    public ResponseEntity<Map<String, String>> assign(@PathVariable Long judgeId, @Valid @RequestBody AssignRequest req) {
        return ResponseEntity.status(201).body(Map.of("message", judgeService.assignEntries(judgeId, req.getEntryIds())));
    }
}
