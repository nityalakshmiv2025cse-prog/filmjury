package com.example.flimjury;

import com.example.flimjury.dto.*;
import com.example.flimjury.entity.Criterion;
import com.example.flimjury.entity.Entry;
import com.example.flimjury.entity.Judge;
import com.example.flimjury.repository.CriterionRepository;
import com.example.flimjury.repository.EntryRepository;
import com.example.flimjury.repository.JudgeRepository;
import com.example.flimjury.service.CriterionService;
import com.example.flimjury.service.EntryService;
import com.example.flimjury.service.JudgeService;
import com.example.flimjury.service.ScoreService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

/** Adds demo data on first start when the database is empty, so the dashboard is not blank. */
@Component
public class DataSeeder implements CommandLineRunner {
    private final EntryRepository entryRepo;
    private final JudgeRepository judgeRepo;
    private final CriterionRepository criterionRepo;
    private final EntryService entryService;
    private final JudgeService judgeService;
    private final CriterionService criterionService;
    private final ScoreService scoreService;
    private final boolean enabled;

    public DataSeeder(EntryRepository entryRepo, JudgeRepository judgeRepo, CriterionRepository criterionRepo,
                      EntryService entryService, JudgeService judgeService, CriterionService criterionService,
                      ScoreService scoreService, @Value("${filmjury.seed-demo-data:true}") boolean enabled) {
        this.entryRepo = entryRepo;
        this.judgeRepo = judgeRepo;
        this.criterionRepo = criterionRepo;
        this.entryService = entryService;
        this.judgeService = judgeService;
        this.criterionService = criterionService;
        this.scoreService = scoreService;
        this.enabled = enabled;
    }

    @Override
    public void run(String... args) {
        if (!enabled || entryRepo.count() > 0 || judgeRepo.count() > 0 || criterionRepo.count() > 0) return;
        try {
            List<Criterion> cr = new ArrayList<>();
            for (String n : new String[]{"Story", "Direction", "Cinematography", "Sound"}) {
                CriterionRequest r = new CriterionRequest();
                r.setName(n);
                r.setMaxScore(10);
                cr.add(criterionService.create(r));
            }
            Judge ravi = judge("Ravi", "ravi@filmjury.com");
            Judge priya = judge("Priya", "priya@filmjury.com");
            Judge arun = judge("Arun", "arun@filmjury.com");
            Entry dawn = entry("Dawn", "Drama", "Team A");
            Entry monsoon = entry("Monsoon", "Thriller", "Team B");
            Entry boats = entry("Paper Boats", "Family", "Team C");
            Entry bus = entry("Last Bus", "Comedy", "Team D");

            judgeService.assignEntries(ravi.getId(), List.of(dawn.getId(), monsoon.getId(), boats.getId(), bus.getId()));
            judgeService.assignEntries(priya.getId(), List.of(dawn.getId(), boats.getId()));
            judgeService.assignEntries(arun.getId(), List.of(monsoon.getId(), boats.getId(), bus.getId()));

            score(dawn, ravi, cr, 8, 7, 8, 7);
            score(dawn, priya, cr, 6, 5, 7, 6);
            score(monsoon, ravi, cr, 9, 9, 8, 9);
            score(monsoon, arun, cr, 8, 9, 9, 8);
            score(boats, ravi, cr, 7, 8, 7, 6);
            score(boats, priya, cr, 8, 7, 8, 8);
            score(boats, arun, cr, 7, 7, 6, 7);
            score(bus, arun, cr, 6, 7, 6, 5);
            System.out.println("Demo data added. Set filmjury.seed-demo-data=false to disable.");
        } catch (Exception ex) {
            System.out.println("Demo data skipped: " + ex.getMessage());
        }
    }

    private Judge judge(String name, String email) {
        JudgeRequest r = new JudgeRequest();
        r.setName(name);
        r.setEmail(email);
        return judgeService.createJudge(r);
    }

    private Entry entry(String title, String genre, String team) {
        EntryRequest r = new EntryRequest();
        r.setTitle(title);
        r.setGenre(genre);
        r.setTeamName(team);
        r.setVideoLink("https://example.com/" + title.toLowerCase().replace(' ', '-'));
        return entryService.createEntry(r);
    }

    private void score(Entry e, Judge j, List<Criterion> cr, int... v) {
        List<CriterionScore> list = new ArrayList<>();
        for (int i = 0; i < v.length; i++) {
            CriterionScore cs = new CriterionScore();
            cs.setCriterionId(cr.get(i).getId());
            cs.setScore(v[i]);
            list.add(cs);
        }
        ScoreRequest r = new ScoreRequest();
        r.setJudgeId(j.getId());
        r.setScores(list);
        scoreService.submitScore(e.getId(), r);
    }
}
