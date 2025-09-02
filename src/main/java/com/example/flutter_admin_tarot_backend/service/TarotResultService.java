package com.example.flutter_admin_tarot_backend.service;

import com.example.flutter_admin_tarot_backend.entity.TarotResult;
import com.example.flutter_admin_tarot_backend.repository.TarotResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarotResultService {
    private final TarotResultRepository repo;

    public TarotResultService(TarotResultRepository repo) {
        this.repo = repo;
    }

    public List<TarotResult> getAllResults() {
        return repo.findAll();
    }

    public TarotResult getResultById(Long id) {
        Optional<TarotResult> result = repo.findById(id);
        return result.orElse(null);
    }

    public TarotResult saveResult(TarotResult result) {
        return repo.save(result);
    }

    public void deleteResult(Long id) {
        repo.deleteById(id);
    }
}

