package com.quesmarkt.quesmarktbase.data.entity;

import jakarta.persistence.ManyToMany;
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
@Table(name = "quiz")
public class Quiz extends BaseEntity<Long> {
    private Long id;
    private String name;
    private HashMap<String,String> filterAttributes;

    @ManyToMany
    private List<Question> questionList;


    @Override
    public String toString() {
        return "Quiz{id=" + id + '}';
    }

    @Override
    public Long getIdentifier() {
        return id;
    }
}
