package com.example.aganitha.controller;



import java.time.Instant;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.aganitha.entity.Paste;
import com.example.aganitha.service.PasteService;
import com.example.aganitha.util.TimeUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping
public class PasteViewController {

    private final PasteService service;

    public PasteViewController(PasteService service) {
        this.service = service;
    }

    @GetMapping("/p/{id}")
    public ResponseEntity<String> view(@PathVariable String id, HttpServletRequest req) {
       Instant now = TimeUtil.now(req);
       Optional<Paste> opt = service.getPaste(id);
        if (opt.isEmpty()) return ResponseEntity.status(404).build();

        Paste p = opt.get();
        if (service.isExpired(p, TimeUtil.now(req))) return ResponseEntity.status(404).build();

        if (!service.incrementView(id, now)) return ResponseEntity.status(404).build();

        String safe = org.springframework.web.util.HtmlUtils.htmlEscape(p.getContent());
        return ResponseEntity.ok()
        	    .contentType(MediaType.TEXT_HTML)
        	    .body("""
        	        <html>
        	          <body>
        	            <pre>%s</pre>
        	          </body>
        	        </html>
        	    """.formatted(safe));

   
        	
    }
}
