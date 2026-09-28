package com.example.flimjury.controller;

import com.example.flimjury.dto.EntryRequest;
import com.example.flimjury.dto.ScoreRequest;
import com.example.flimjury.entity.Entry;
import com.example.flimjury.entity.ScoreCard;
import com.example.flimjury.service.EntryService;
import com.example.flimjury.service.ScoreService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/entries")
public class EntryController {
    private final EntryService entryService;
    private final ScoreService scoreService;

    public EntryController(EntryService entryService, ScoreService scoreService) {
        this.entryService = entryService;
        this.scoreService = scoreService;
    }

    @PostMapping
    public ResponseEntity<Entry> create(@Valid @RequestBody EntryRequest req) {
        return ResponseEntity.status(201).body(entryService.createEntry(req));
    }

    @GetMapping
    public List<Entry> getAll() { return entryService.getAll(); }

    @GetMapping("/{id}")
    public Entry getById(@PathVariable Long id) { return entryService.getById(id); }

    @PostMapping("/{entryId}/scores")
    public ResponseEntity<ScoreCard> submitScore(@PathVariable Long entryId, @Valid @RequestBody ScoreRequest req) {
        return ResponseEntity.status(201).body(scoreService.submitScore(entryId, req));
    }

    @GetMapping("/{entryId}/average")
    public Map<String, Object> average(@PathVariable Long entryId) {
        return Map.of("entryId", entryId, "averageScore", scoreService.getAverageScore(entryId));
    }
}
