package com.example.stackoverflow.controller;

import com.example.stackoverflow.entity.AnswerEntity;
import com.example.stackoverflow.entity.QuestionEntity;
import com.example.stackoverflow.entity.UserEntity;
import com.example.stackoverflow.repository.AnswerRepository;
import com.example.stackoverflow.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"}, allowCredentials = "true")
public class AnswerController {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    // إضافة جواب لسؤال محدد
    // الـ URL غيكون هو: POST /api/questions/{questionId}/answers
    @PostMapping("/{questionId}/answers")
    public ResponseEntity<AnswerEntity> addAnswer(
            @PathVariable Long questionId,
            @RequestBody Map<String, String> requestBody,
            Authentication authentication) {

        // 1. كنجيبو السؤال من الداتابيز
        QuestionEntity question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question introuvable"));

        // 2. كنجيبو المستخدم اللي ميكونيكطي دابا
        UserEntity user = (UserEntity) authentication.getPrincipal();

        // 3. كنصايبو الجواب الجديد
        AnswerEntity answer = new AnswerEntity();
        answer.setBody(requestBody.get("body"));
        answer.setQuestion(question);
        answer.setUser(user);

        // 4. كنحفظو الجواب
        AnswerEntity savedAnswer = answerRepository.save(answer);

        return ResponseEntity.ok(savedAnswer);
    }
}