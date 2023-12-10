package com.quesmarkt.quesmarktbase.data.entity;

import com.quesmarkt.quesmarktbase.data.converter.LongListConverter;
import com.quesmarkt.quesmarktbase.data.enums.UserQuizState;
import jakarta.persistence.Convert;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author anercan
 */

@Getter
@Setter
@Table(name = "user_quiz")
public class UserQuiz {
    private Long userId;
    private Quiz quiz;
    private int timeTaken;
    private UserQuizState state;
    private ZonedDateTime completeDate;

    @Convert(converter = LongListConverter.class)
    private List<Long> correctAnswers;

    @Convert(converter = LongListConverter.class)
    private List<Long> wrongAnswers;

}
