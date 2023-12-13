package com.quesmarkt.quesmarktbase.data.response;

import com.quesmarkt.quesmarktbase.data.entity.Question;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

/**
 * @author anercan
 */

@EqualsAndHashCode(callSuper = false)
@SuperBuilder
public class QuizResponse extends ServiceResponse {
    private Long id;
    private String name;
    private Map<String, String> attributes;
    private List<Question> questionList;
    private UserQuizResponse userQuiz;
}
