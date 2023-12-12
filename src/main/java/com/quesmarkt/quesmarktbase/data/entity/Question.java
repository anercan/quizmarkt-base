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
@Table(name = "question")
public class Question extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String imgUrl;
    private Long correctAnswerId;
    private boolean active;
    private String explanation;
    private int priority;

    @ElementCollection(targetClass = String.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "QUESTION_ATTRIBUTES", joinColumns = @JoinColumn(name = "question_id"))
    @MapKeyColumn(name = "param_key", length = 64)
    @Column(name = "param_value", length = 512)
    private Map<String, String> attributes; //subject-xx,yy etc.

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id")
    private List<Answers> answersList;

    @Override
    public String toString() {
        return "Question{id=" + id + '}';
    }

    @Override
    public Long getId() {
        return id;
    }
}
