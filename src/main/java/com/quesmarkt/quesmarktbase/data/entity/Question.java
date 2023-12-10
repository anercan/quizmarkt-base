package com.quesmarkt.quesmarktbase.data.entity;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * @author anercan
 */

@Getter
@Setter
@Table(name = "question")
public class Question extends BaseEntity<Long> {
    private Long id;
    private String content;
    private String imgUrl;
    private Long correctAnswerId;
    private HashMap<String,String> attributes; //subject-xx,yy etc.

    @OneToMany
    private List<Answers> answersList;

    @ManyToMany
    private List<Question> questionList;

    @Override
    public String toString() {
        return "Question{id=" + id + '}';
    }

    @Override
    public Long getIdentifier() {
        return id;
    }
}
