package com.quizmarkt.base.data.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author anercan
 */

@Data
@Builder
public class UserDailyQuizWithInfoResponse {
    private List<QuestionResponse> questionList;
    private UserDailyQuizResponse userDailyQuizResponse;
}
