package com.example.aganitha.controller;


import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.aganitha.dto.PasteRequest;
import com.example.aganitha.entity.Paste;
import com.example.aganitha.service.PasteService;
import com.example.aganitha.util.TimeUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pastes")
public class PasteApiController {

    private final PasteService service;

    public PasteApiController(PasteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody PasteRequest req, HttpServletRequest http) {
        if (req.content() == null || req.content().isBlank()) {
            return ResponseEntity.status(400).body(Map.of("error", "Content is required and cannot be empty"));
        }
        if (req.ttl_seconds() != null && req.ttl_seconds() < 1) {
            return ResponseEntity.status(400).body(Map.of("error", "ttl_seconds must be ≥ 1"));
        }
        if (req.max_views() != null && req.max_views() < 1) {
            return ResponseEntity.status(400).body(Map.of("error", "max_views must be ≥ 1"));
        }

        try {
            String id = UUID.randomUUID().toString().substring(0, 8);

            Paste p = new Paste();
            p.setId(id);
            p.setContent(req.content());
            p.setMaxViews(req.max_views());

            if (req.ttl_seconds() != null) {
                p.setExpiresAt(TimeUtil.now(http).plusSeconds(req.ttl_seconds()));
            }

            service.createPaste(p);

            String base = http.getRequestURL().toString().replace("/api/pastes", "");

            return ResponseEntity.ok(Map.of(
                "id", id,
                "url", base + "/p/" + id
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Internal Server Error", "details", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<?> fetch(@PathVariable String id, HttpServletRequest req) {
        System.out.println("Fetching paste with id: " + id);

        Optional<Paste> opt = service.getPaste(id);
        if (opt.isEmpty()) {
            System.out.println("Paste not found for id: " + id);
            return notFound();
        }

        Paste p = opt.get();
        Instant now = TimeUtil.now(req);
        System.out.println("Current time: " + now);
        System.out.println("Paste expires at: " + p.getExpiresAt());

        if (service.isExpired(p, now)) {
            System.out.println("Paste expired for id: " + id);
            return notFound();
        }

        boolean incremented = service.incrementView(id, now);
        if (!incremented) {
            return notFound();
        }

       
        Paste updated = service.getPaste(id).orElseThrow();
        Integer remaining = service.getRemainingViews(updated);

        return ResponseEntity.ok(Map.of(
            "content", updated.getContent(),
            "remaining_views", remaining,
            "expires_at", updated.getExpiresAt()
        ));
    }

    private ResponseEntity<Map<String, String>> notFound() {
        return ResponseEntity.status(404)
            .body(Map.of("error", "Paste not found"));
    }
}
