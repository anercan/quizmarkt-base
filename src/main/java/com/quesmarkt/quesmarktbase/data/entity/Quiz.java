package com.quesmarkt.quesmarktbase.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @author anercan
 */

@Getter
@Setter
@Entity
@Table(name = "quiz")
public class Quiz extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private boolean active;

    @ElementCollection(targetClass = String.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "QUIZ_ATTRIBUTES", joinColumns = @JoinColumn(name = "quiz_id"))
    @MapKeyColumn(name = "param_key", length = 64)
    @Column(name = "param_value", length = 512)
    private Map<String, String> attributes;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "QUIZ_QUESTIONS", joinColumns = @JoinColumn(name = "quiz_id"), inverseJoinColumns = @JoinColumn(name = "question_id"))
    private List<Question> questionList;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "QUIZ_GROUP_QUIZZES", joinColumns = @JoinColumn(name = "quiz_id"), inverseJoinColumns = @JoinColumn(name = "quiz_group_id"))
    private List<QuizGroup> quizGroupList;

    @Override
    public String toString() {
        return "Quiz{id=" + id + '}';
    }

    @Override
    public Long getId() {
        return id;
    }
}
