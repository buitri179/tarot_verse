package com.example.flutter_admin_tarot_backend.controller;

import com.example.flutter_admin_tarot_backend.entity.TarotResult;
import com.example.flutter_admin_tarot_backend.service.TarotResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarot-results")
@CrossOrigin(origins = "*")
public class TarotResultController {

    private final TarotResultService tarotService;

    public TarotResultController(TarotResultService tarotService) {
        this.tarotService = tarotService;
    }

    @GetMapping
    public List<TarotResult> getAllResults() {
        return tarotService.getAllResults();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarotResult> getResultById(@PathVariable Long id) {
        TarotResult result = tarotService.getResultById(id);
        if (result == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<TarotResult> createResult(@RequestBody TarotResult result) {
        TarotResult saved = tarotService.saveResult(result);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteResult(@PathVariable Long id) {
        tarotService.deleteResult(id);
        return ResponseEntity.ok().build();
    }
}