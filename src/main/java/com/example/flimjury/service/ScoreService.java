package com.example.flimjury.service;

import com.example.flimjury.dto.CriterionScore;
import com.example.flimjury.dto.LeaderboardItem;
import com.example.flimjury.dto.ScoreRequest;
import com.example.flimjury.entity.Criterion;
import com.example.flimjury.entity.Entry;
import com.example.flimjury.entity.Judge;
import com.example.flimjury.entity.ScoreCard;
import com.example.flimjury.entity.ScoreItem;
import com.example.flimjury.exception.BusinessRuleException;
import com.example.flimjury.exception.ResourceNotFoundException;
import com.example.flimjury.repository.CriterionRepository;
import com.example.flimjury.repository.EntryRepository;
import com.example.flimjury.repository.JudgeRepository;
import com.example.flimjury.repository.ScoreCardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class ScoreService {
    private final EntryRepository entryRepo;
    private final JudgeRepository judgeRepo;
    private final CriterionRepository criterionRepo;
    private final ScoreCardRepository scoreCardRepo;

    public ScoreService(EntryRepository entryRepo, JudgeRepository judgeRepo,
                        CriterionRepository criterionRepo, ScoreCardRepository scoreCardRepo) {
        this.entryRepo = entryRepo;
        this.judgeRepo = judgeRepo;
        this.criterionRepo = criterionRepo;
        this.scoreCardRepo = scoreCardRepo;
    }

    @Transactional
    public ScoreCard submitScore(Long entryId, ScoreRequest req) {
        Entry entry = entryRepo.findById(entryId)
            .orElseThrow(() -> new ResourceNotFoundException("Entry not found: " + entryId));
        Judge judge = judgeRepo.findById(req.getJudgeId())
            .orElseThrow(() -> new ResourceNotFoundException("Judge not found: " + req.getJudgeId()));

        boolean assigned = entry.getAssignedJudges().stream().anyMatch(j -> j.getId().equals(judge.getId()));
        if (!assigned) throw new BusinessRuleException("Judge is not assigned to this entry");
        if (scoreCardRepo.existsByJudgeIdAndEntryId(judge.getId(), entryId))
            throw new BusinessRuleException("Judge already submitted a scorecard for this entry");

        ScoreCard card = new ScoreCard();
        card.setJudge(judge);
        card.setEntry(entry);
        Set<Long> seen = new HashSet<>();
        double total = 0;
        for (CriterionScore cs : req.getScores()) {
            if (!seen.add(cs.getCriterionId()))
                throw new BusinessRuleException("Duplicate criterion in request: " + cs.getCriterionId());
            Criterion c = criterionRepo.findById(cs.getCriterionId())
                .orElseThrow(() -> new ResourceNotFoundException("Criterion not found: " + cs.getCriterionId()));
            if (cs.getScore() > c.getMaxScore())
                throw new BusinessRuleException("Score exceeds max (" + c.getMaxScore() + ") for " + c.getName());
            ScoreItem item = new ScoreItem();
            item.setScoreCard(card);
            item.setCriterion(c);
            item.setScore(cs.getScore());
            card.getItems().add(item);
            total += cs.getScore();
        }
        card.setTotalScore(total);
        ScoreCard saved = scoreCardRepo.save(card);
        System.out.println("NOTIFICATION: Judge " + judge.getName() + " scored '" + entry.getTitle() + "' with total " + total);
        return saved;
    }

    public double getAverageScore(Long entryId) {
        if (!entryRepo.existsById(entryId)) throw new ResourceNotFoundException("Entry not found: " + entryId);
        return averageFor(entryId);
    }

    private double averageFor(Long entryId) {
        return scoreCardRepo.findByEntryId(entryId).stream().mapToDouble(ScoreCard::getTotalScore).average().orElse(0.0);
    }

    public List<LeaderboardItem> getLeaderboard() {
        List<Entry> entries = new ArrayList<>(entryRepo.findAll());
        Map<Long, Double> avg = new HashMap<>();
        for (Entry e : entries) avg.put(e.getId(), averageFor(e.getId()));
        entries.sort(Comparator.comparingDouble((Entry e) -> avg.get(e.getId())).reversed());

        List<LeaderboardItem> result = new ArrayList<>();
        int pos = 0, rank = 0;
        Double prev = null;
        for (Entry e : entries) {
            pos++;
            double a = avg.get(e.getId());
            if (prev == null || a != prev) rank = pos;
            prev = a;
            result.add(new LeaderboardItem(e.getId(), e.getTitle(), e.getTeamName(), a, rank));
        }
        return result;
    }
}
