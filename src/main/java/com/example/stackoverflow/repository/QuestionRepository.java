package com.example.stackoverflow.repository;

import com.example.stackoverflow.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
    // دالة البحث بالعنوان (تجاهل حالة الأحرف)
    List<QuestionEntity> findByTitleContainingIgnoreCaseOrderByCreatedAtDesc(String title);

    List<QuestionEntity> findAllByOrderByCreatedAtDesc();
}