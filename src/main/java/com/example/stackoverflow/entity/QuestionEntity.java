package com.example.stackoverflow.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List; // زيدي هادي
import java.util.Set;

@Entity
@Table(name = "questions")
@Getter
@Setter
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String body;

    private Integer viewCount = 0;
    private Integer voteCount = 0;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"questions", "password", "answers", "comments", "votes", "notifications"})
    private UserEntity user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "question_tags", joinColumns = @JoinColumn(name = "question_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @JsonIgnoreProperties("questions")
    private Set<TagEntity> tags = new HashSet<>();

    // --- التعديل الضروري هنا ---
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("question") // باش ما يوقعش Infinite Loop
    private List<AnswerEntity> answers;

    // ميطود كتحسب عدد الأجوبة باش تسهل على الفرونت
    public int getAnswersCount() {
        return (answers != null) ? answers.size() : 0;
    }
    // -------------------------

    public UserEntity getAuthor() { return this.user; }
    public String getContent() { return this.body; }

    @PrePersist
    protected void onCreate() { this.createdAt = LocalDateTime.now(); }
}