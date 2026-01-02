package com.example.stackoverflow.controller;

import com.example.stackoverflow.entity.QuestionEntity;
import com.example.stackoverflow.entity.UserEntity;
import com.example.stackoverflow.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    // جلب كل الأسئلة
    @GetMapping
    public List<QuestionEntity> getAllQuestions() {
        return questionRepository.findAllByOrderByCreatedAtDesc();
    }

    // إضافة سؤال جديد
    @PostMapping
    public ResponseEntity<QuestionEntity> createQuestion(
            @RequestBody QuestionEntity question,
            Authentication authentication) {

        UserEntity user = (UserEntity) authentication.getPrincipal();
        question.setUser(user);
        question.setViewCount(0);
        question.setVoteCount(0);

        QuestionEntity saved = questionRepository.save(question);
        return ResponseEntity.ok(saved);
    }

    // جلب سؤال بواسطة ID (للـ detail)
    @GetMapping("/{id}")
    public ResponseEntity<QuestionEntity> getQuestion(@PathVariable Long id) {
        return questionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}