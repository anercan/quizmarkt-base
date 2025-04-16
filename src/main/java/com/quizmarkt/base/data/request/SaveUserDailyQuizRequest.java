package com.quizmarkt.base.data.request;

import lombok.Builder;
import lombok.Data;

/**
 * @author anercan
 */

@Data
@Builder
public class SaveUserDailyQuizRequest {
    private Long correctQuestionId;
    private Long wrongQuestionId;
}
