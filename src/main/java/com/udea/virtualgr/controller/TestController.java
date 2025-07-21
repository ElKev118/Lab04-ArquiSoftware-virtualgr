package com.udea.virtualgr.controller;

import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping
    public Map<String, Object> test() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "API de prueba funcionando");
        response.put("timestamp", LocalDateTime.now());
        response.put("service", "VirtualGR");
        return response;
    }

    @PostMapping("/graphql")
    public Map<String, Object> testGraphQL(@RequestBody(required = false) Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Endpoint GraphQL de prueba");
        response.put("received", request);
        response.put("actualEndpoint", "/graphql");
        response.put("timestamp", LocalDateTime.now());
        return response;
    }
}