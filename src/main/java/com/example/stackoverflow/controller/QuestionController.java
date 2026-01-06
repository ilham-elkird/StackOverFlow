package com.example.stackoverflow.controller;

import com.example.stackoverflow.entity.QuestionEntity;
import com.example.stackoverflow.entity.TagEntity;
import com.example.stackoverflow.entity.UserEntity;
import com.example.stackoverflow.repository.QuestionRepository;
import com.example.stackoverflow.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"}, allowCredentials = "true")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TagRepository tagRepository;

    @GetMapping
    public List<QuestionEntity> getAllQuestions(@RequestParam(required = false) String search) {
        if (search != null && !search.trim().isEmpty()) {
            return questionRepository.findByTitleContainingIgnoreCaseOrderByCreatedAtDesc(search);
        }
        return questionRepository.findAllByOrderByCreatedAtDesc();
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionEntity> getQuestionById(@PathVariable Long id) {
        return questionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/vote")
    public ResponseEntity<QuestionEntity> voteQuestion(@PathVariable Long id, @RequestParam String direction) {
        QuestionEntity question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question introuvable"));
        if ("up".equalsIgnoreCase(direction)) question.setVoteCount(question.getVoteCount() + 1);
        else if ("down".equalsIgnoreCase(direction)) question.setVoteCount(question.getVoteCount() - 1);
        return ResponseEntity.ok(questionRepository.save(question));
    }

    @PostMapping
    public ResponseEntity<QuestionEntity> createQuestion(@RequestBody QuestionRequest request, Authentication authentication) {
        UserEntity user = (UserEntity) authentication.getPrincipal();
        QuestionEntity question = new QuestionEntity();
        question.setTitle(request.getTitle());
        question.setBody(request.getBody());
        question.setUser(user);
        question.setVoteCount(0);
        question.setViewCount(0);
        if (request.getTags() != null) {
            Set<TagEntity> tags = request.getTags().stream().map(tagName ->
                    tagRepository.findByName(tagName.toLowerCase())
                            .orElseGet(() -> {
                                TagEntity newTag = new TagEntity();
                                newTag.setName(tagName.toLowerCase());
                                return tagRepository.save(newTag);
                            })
            ).collect(Collectors.toSet());
            question.setTags(tags);
        }
        return ResponseEntity.ok(questionRepository.save(question));
    }

    public static class QuestionRequest {
        private String title; private String body; private List<String> tags;
        public String getTitle() { return title; } public void setTitle(String title) { this.title = title; }
        public String getBody() { return body; } public void setBody(String body) { this.body = body; }
        public List<String> getTags() { return tags; } public void setTags(List<String> tags) { this.tags = tags; }
    }
}