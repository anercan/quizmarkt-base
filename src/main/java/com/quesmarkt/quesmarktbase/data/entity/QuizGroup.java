package com.quesmarkt.quesmarktbase.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @Override
    public String toString() {
        return "QuizGroup{id=" + id + '}';
    }

    @Override
    public Long getId() {
        return id;
    }
}
