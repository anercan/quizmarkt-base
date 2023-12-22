package com.quesmarkt.quesmarktbase.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author anercan
 */

@Getter
@Setter
@Entity
@Table(name = "quiz_group")
public class QuizGroup extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String color;
    private String imageUrl;
    private boolean active;
    private int appId;
    private int priority;
    private int quizQuantity;

    @ElementCollection(targetClass = String.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "QUIZ_GROUP_ATTRIBUTES", joinColumns = @JoinColumn(name = "quiz_id"))
    @MapKeyColumn(name = "param_key", length = 64)
    @Column(name = "param_value", length = 512)
    private Map<String, String> attributes;

    @Override
    public String toString() {
        return "QuizGroup{id=" + id + '}';
    }

    @Override
    public Long getId() {
        return id;
    }
}
