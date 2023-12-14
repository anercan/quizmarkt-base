package com.quesmarkt.quesmarktbase.data.response;

import lombok.Data;

/**
 * @author anercan
 */

@Data
public class UserWrongAnswerResponse {
    private AnswerResponse wrongAnswer;
    private QuestionResponseForReview question;
}
