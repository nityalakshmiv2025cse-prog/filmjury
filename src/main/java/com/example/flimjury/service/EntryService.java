package com.example.flimjury.service;

import com.example.flimjury.dto.EntryRequest;
import com.example.flimjury.entity.Entry;
import com.example.flimjury.exception.ResourceNotFoundException;
import com.example.flimjury.repository.EntryRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EntryService {
    private final EntryRepository entryRepo;

    public EntryService(EntryRepository entryRepo) { this.entryRepo = entryRepo; }

    public Entry createEntry(EntryRequest req) {
        Entry e = new Entry();
        e.setTitle(req.getTitle());
        e.setGenre(req.getGenre());
        e.setVideoLink(req.getVideoLink());
        e.setTeamName(req.getTeamName());
        return entryRepo.save(e);
    }

    public List<Entry> getAll() { return entryRepo.findAll(); }

    public Entry getById(Long id) {
        return entryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entry not found: " + id));
    }
}
