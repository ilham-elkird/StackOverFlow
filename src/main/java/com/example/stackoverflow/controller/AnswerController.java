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
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"}, allowCredentials = "true")
public class AnswerController {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/questions/{questionId}/answers")
    public ResponseEntity<AnswerEntity> addAnswer(@PathVariable Long questionId, @RequestBody Map<String, String> requestBody, Authentication authentication) {
        QuestionEntity question = questionRepository.findById(questionId).orElseThrow(() -> new RuntimeException("Question introuvable"));
        UserEntity user = (UserEntity) authentication.getPrincipal();
        AnswerEntity answer = new AnswerEntity();
        answer.setBody(requestBody.get("body"));
        answer.setQuestion(question);
        answer.setUser(user);
        answer.setVoteCount(0);
        return ResponseEntity.ok(answerRepository.save(answer));
    }

    @PostMapping("/answers/{id}/vote") // مسار خاص بتصويت الجواب
    public ResponseEntity<AnswerEntity> voteAnswer(@PathVariable Long id, @RequestParam String direction) {
        AnswerEntity answer = answerRepository.findById(id).orElseThrow(() -> new RuntimeException("Réponse introuvable"));
        if ("up".equalsIgnoreCase(direction)) answer.setVoteCount(answer.getVoteCount() + 1);
        else if ("down".equalsIgnoreCase(direction)) answer.setVoteCount(answer.getVoteCount() - 1);
        return ResponseEntity.ok(answerRepository.save(answer));
    }
}