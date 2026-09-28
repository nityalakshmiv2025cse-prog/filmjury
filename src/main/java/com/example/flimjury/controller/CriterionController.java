package com.example.flimjury.controller;

import com.example.flimjury.dto.CriterionRequest;
import com.example.flimjury.entity.Criterion;
import com.example.flimjury.service.CriterionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/criteria")
public class CriterionController {
    private final CriterionService criterionService;

    public CriterionController(CriterionService criterionService) { this.criterionService = criterionService; }

    @PostMapping
    public ResponseEntity<Criterion> create(@Valid @RequestBody CriterionRequest req) {
        return ResponseEntity.status(201).body(criterionService.create(req));
    }

    @GetMapping
    public List<Criterion> getAll() { return criterionService.getAll(); }
}
