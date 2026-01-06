package com.example.stackoverflow.repository;
import com.example.stackoverflow.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;



import com.example.stackoverflow.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TagRepository extends JpaRepository<TagEntity, Long> {
    Optional<TagEntity> findByName(String name);
}
