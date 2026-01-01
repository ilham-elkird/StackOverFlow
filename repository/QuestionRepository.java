package com.example.stackoverflow.repository;
import com.example.stackoverflow.entity.QuestionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
    Page<QuestionEntity> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Page<QuestionEntity> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
}