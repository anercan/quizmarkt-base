package com.quizmarkt.base.data.request;

import lombok.Data;

/**
 * @author anercan
 */

@Data
public class UserWrongAnswerRequest {
    private Long questionId;
    private Long answerId;
}
