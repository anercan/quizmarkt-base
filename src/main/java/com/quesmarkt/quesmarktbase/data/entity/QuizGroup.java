package com.quesmarkt.quesmarktbase.data.entity;

import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author anercan
 */

@Getter
@Setter
@Table(name = "quiz_group")
public class QuizGroup extends BaseEntity<Long> {
    private Long id;
    private String title;
    private String description;
    private String color;
    private String imageUrl;
    //private QuizGroup quizGroup;
    private List<Quiz> quizList;

    @Override
    public String toString() {
        return "QuizGroup{id=" + id + '}';
    }

    @Override
    public Long getIdentifier() {
        return id;
    }
}
