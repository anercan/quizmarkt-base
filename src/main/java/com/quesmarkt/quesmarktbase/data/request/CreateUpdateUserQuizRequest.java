package com.quesmarkt.quesmarktbase.data.request;

import lombok.Data;

/**
 * @author anercan
 */

@Data
public class CreateUpdateUserQuizRequest {
    private Long quizId;
    private Long quizGroupId;
    private Long correctQuestionId;
    private UserWrongAnswerRequest userWrongAnswerRequest;
}
