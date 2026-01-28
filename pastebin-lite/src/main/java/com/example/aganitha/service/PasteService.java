package com.example.aganitha.service;

import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.aganitha.entity.Paste;
import com.example.aganitha.repository.PasteRepository;

import jakarta.transaction.Transactional;

@Service
public class PasteService {
    private final PasteRepository repo;

    public PasteService(PasteRepository repo) {
        this.repo = repo;
    }

    public Paste createPaste(Paste paste) {
        return repo.save(paste);
    }

    public Optional<Paste> getPaste(String id) {
        return repo.findById(id);
    }

    public boolean isExpired(Paste paste, Instant now) {
        return paste.getExpiresAt() != null && now.isAfter(paste.getExpiresAt());
    }

    @Transactional
    public boolean incrementView(String id, Instant now) {

    	int updated = repo.incrementView(id, now);
  
        return updated > 0;
    }
    public Integer getRemainingViews(Paste paste) {
        if (paste.getMaxViews() == null) return null;
        return Math.max(paste.getMaxViews() - paste.getViewCount(), 0);
    }

    
	    
}
