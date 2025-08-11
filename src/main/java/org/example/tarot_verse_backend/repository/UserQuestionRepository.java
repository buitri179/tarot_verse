package org.example.tarot_verse_backend.repository;

import org.example.tarot_verse_backend.entity.UserQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserQuestionRepository extends JpaRepository<UserQuestion, Long> {
}
