package com.quizmarkt.base.data.entity;

import com.quizmarkt.base.data.converter.PremiumTypeListConverter;
import com.quizmarkt.base.data.enums.PremiumType;
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
    private int priority;
    private int appId;
    private int activeQuestionCount;

    @ElementCollection(targetClass = String.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "QUIZ_ATTRIBUTES", joinColumns = @JoinColumn(name = "quiz_id"))
    @MapKeyColumn(name = "param_key", length = 64)
    @Column(name = "param_value", length = 512)
    private Map<String, String> attributes;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "QUIZ_QUESTIONS", joinColumns = @JoinColumn(name = "quiz_id"), inverseJoinColumns = @JoinColumn(name = "question_id"))
    private List<Question> questionList;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "QUIZ_GROUP_QUIZZES", joinColumns = @JoinColumn(name = "quiz_id"), inverseJoinColumns = @JoinColumn(name = "quiz_group_id"))
    private List<QuizGroup> quizGroupList;

    @Convert(converter = PremiumTypeListConverter.class)
    private List<PremiumType> availablePremiumTypes;

    @Override
    public String toString() {
        return "Quiz{id=" + id + '}';
    }

    @Override
    public Long getId() {
        return id;
    }
}
