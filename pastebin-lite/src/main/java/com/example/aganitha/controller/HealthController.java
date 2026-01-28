package com.example.aganitha.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HealthController {

	  @GetMapping("/api/healthz")
	    public ResponseEntity<Map<String, Boolean>> health() {
	        return ResponseEntity.ok(Map.of("ok", true));
	    }
}
