package com.example.aganitha.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.*;

public record PasteRequest(
	    @NotBlank String content,
	    @Min(value = 1, message = "ttl_seconds must be ≥ 1")Integer ttl_seconds,
	    @Min(value = 1, message = "max_views must be ≥ 1") Integer max_views
	) {}
