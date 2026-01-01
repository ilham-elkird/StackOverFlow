package com.example.stackoverflow.repository;
import com.example.stackoverflow.entity.NotificationEntity;
import com.example.stackoverflow.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    List<NotificationEntity> findByUserOrderByCreatedAtDesc(UserEntity user);
}