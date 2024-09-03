package com.quizmarkt.base.data.response;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author anercan
 */

@Data
public class QuestionResponse {
    private Long id;
    private String content;
    private String imgUrl;
    private Long correctAnswerId;
    private String explanation;
    private int priority;
    private Map<String, String> attributes; //subject-xx,yy etc.
    private List<AnswerResponse> answersList;
}
