package com.quizmarkt.base.data.entity;

import com.quizmarkt.base.data.converter.LongListConverter;
import com.quizmarkt.base.data.enums.UserQuizState;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author anercan
 */

@Getter
@Setter
@Entity
@Table(name = "user_daily_quiz")
public class UserDailyQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private UserQuizState state;
    private LocalDate createdDate;
    private int appId;

    @Convert(converter = LongListConverter.class)
    private List<Long> correctQuestionIdList = new ArrayList<>();

    @Convert(converter = LongListConverter.class)
    private List<Long> wrongQuestionIdList = new ArrayList<>();

    @Override
    public String toString() {
        return "UserQuiz{id=" + id + '}';
    }

}
