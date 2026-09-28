package com.example.flimjury.service;

import com.example.flimjury.dto.JudgeRequest;
import com.example.flimjury.entity.Entry;
import com.example.flimjury.entity.Judge;
import com.example.flimjury.exception.ResourceNotFoundException;
import com.example.flimjury.repository.EntryRepository;
import com.example.flimjury.repository.JudgeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class JudgeService {
    private final JudgeRepository judgeRepo;
    private final EntryRepository entryRepo;

    public JudgeService(JudgeRepository judgeRepo, EntryRepository entryRepo) {
        this.judgeRepo = judgeRepo;
        this.entryRepo = entryRepo;
    }

    public Judge createJudge(JudgeRequest req) {
        Judge j = new Judge();
        j.setName(req.getName());
        j.setEmail(req.getEmail());
        return judgeRepo.save(j);
    }

    public List<Judge> getAll() { return judgeRepo.findAll(); }

    @Transactional
    public String assignEntries(Long judgeId, List<Long> entryIds) {
        Judge judge = judgeRepo.findById(judgeId)
            .orElseThrow(() -> new ResourceNotFoundException("Judge not found: " + judgeId));
        for (Long id : entryIds) {
            Entry entry = entryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entry not found: " + id));
            boolean already = entry.getAssignedJudges().stream().anyMatch(j -> j.getId().equals(judge.getId()));
            if (!already) entry.getAssignedJudges().add(judge);
            entryRepo.save(entry);
        }
        return "Assigned " + entryIds.size() + " entries to judge " + judge.getName();
    }
}
