package com.quizmarkt.base.data.entity;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wrong_answer_id")
    private Answer wrongAnswer;

    @Override
    public String toString() {
        return "UserWrongAnswer{id=" + id + '}';
    }
}
