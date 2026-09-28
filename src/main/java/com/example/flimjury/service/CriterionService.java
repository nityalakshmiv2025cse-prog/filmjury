package com.example.flimjury.service;

import com.example.flimjury.dto.CriterionRequest;
import com.example.flimjury.entity.Criterion;
import com.example.flimjury.repository.CriterionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CriterionService {
    private final CriterionRepository criterionRepo;

    public CriterionService(CriterionRepository criterionRepo) { this.criterionRepo = criterionRepo; }

    public Criterion create(CriterionRequest req) {
        Criterion c = new Criterion();
        c.setName(req.getName());
        c.setMaxScore(req.getMaxScore());
        return criterionRepo.save(c);
    }

    public List<Criterion> getAll() { return criterionRepo.findAll(); }
}
