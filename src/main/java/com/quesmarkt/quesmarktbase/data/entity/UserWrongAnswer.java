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
@Table(name = "user_wrong_answer")
public class UserWrongAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @OneToOne
    @JoinColumn(name = "wrong_answer_id")
    private Answers wrongAnswer;

    @Override
    public String toString() {
        return "UserWrongAnswer{id=" + id + '}';
    }
}
