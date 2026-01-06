package com.example.stackoverflow.repository;

import com.example.stackoverflow.entity.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {
    // كيجيب الأجوبة ديال سؤال معين مرتبة من الأحدث للأقدم
    List<AnswerEntity> findByQuestionIdOrderByCreatedAtDesc(Long questionId);
}