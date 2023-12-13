package com.quesmarkt.quesmarktbase.data.entity;

import com.quesmarkt.quesmarktbase.data.converter.LongListConverter;
import com.quesmarkt.quesmarktbase.data.enums.UserQuizState;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author anercan
 */

@Getter
@Setter
@Entity
@Table(name = "user_quiz")
public class UserQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private int timeTaken;
    private UserQuizState state;
    private ZonedDateTime completeDate;
    private int appId; // todo dbde check et Longdu
    private Long quizGroupId;

    @OneToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @Convert(converter = LongListConverter.class)
    private List<Long> correctQuestionList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_quiz_id")
    private List<UserWrongAnswer> wrongQuestionList = new ArrayList<>();

    @Override
    public String toString() {
        return "UserQuiz{id=" + id + '}';
    }

    public Long getQuizId() {
        return quiz.getId();
    }
}
