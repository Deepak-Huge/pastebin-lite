package com.example.aganitha.util;

import java.time.Instant;

import jakarta.servlet.http.HttpServletRequest;

public class TimeUtil {
    private static final boolean TEST_MODE = "1".equals(System.getenv("TEST_MODE"));

    public static Instant now(HttpServletRequest req) {
        if (TEST_MODE) {
            String h = req.getHeader("x-test-now-ms");
            if (h != null) {
                try {
                    return Instant.ofEpochMilli(Long.parseLong(h));
                } catch (NumberFormatException e) {
                }
            }
        }
        return Instant.now();
    }
}
