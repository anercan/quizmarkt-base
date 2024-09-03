package com.quizmarkt.base.data.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

/**
 * @author anercan
 */

@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@Data
public class QuizResponse extends ServiceResponse {
    private Long id;
    private String name;
    private Map<String, String> attributes;
    private List<QuestionResponse> questionList;
    private UserQuizResponse userQuiz;
}
