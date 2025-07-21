package com.udea.virtualgr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/")
    public Map<String, Object> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "VirtualGR GraphQL API está funcionando!");
        response.put("timestamp", LocalDateTime.now());
        response.put("endpoints", Map.of(
                "graphql", "/graphql",
                "actuator", "/actuator",
                "health", "/actuator/health"
        ));
        return response;
    }

    @GetMapping("/api/status")
    public Map<String, Object> status() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "VirtualGR");
        response.put("version", "1.0.0");
        response.put("timestamp", LocalDateTime.now());
        return response;
    }
}