package com.example.stackoverflow.repository;
import com.example.stackoverflow.entity.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {
    List<AnswerEntity> findByQuestionIdOrderByCreatedAtDesc(Long questionId);
}
