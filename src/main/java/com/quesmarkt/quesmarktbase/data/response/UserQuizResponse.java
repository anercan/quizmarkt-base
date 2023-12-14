package com.quesmarkt.quesmarktbase.data.response;

import com.quesmarkt.quesmarktbase.data.enums.UserQuizState;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author anercan
 */

@Data
public class UserQuizResponse {
    private UserQuizState state;
    private List<Long> correctQuestionList = new ArrayList<>();
    private List<UserWrongAnswerResponse> wrongQuestionList = new ArrayList<>();
}
