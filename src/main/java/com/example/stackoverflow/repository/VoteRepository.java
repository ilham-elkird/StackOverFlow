package com.example.stackoverflow.repository;
import com.example.stackoverflow.entity.AnswerEntity;
import com.example.stackoverflow.entity.QuestionEntity;
import com.example.stackoverflow.entity.UserEntity;
import com.example.stackoverflow.entity.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface VoteRepository extends JpaRepository<VoteEntity, Long> {
    Optional<VoteEntity> findByUserAndQuestion(UserEntity user, QuestionEntity question);
    Optional<VoteEntity> findByUserAndAnswer(UserEntity user, AnswerEntity answer);
}
