package com.quizmarkt.base.data.response;

import com.quizmarkt.base.data.enums.UserQuizState;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author anercan
 */

@Data
public class UserQuizInListResponse {
    private Long id;
    private int timeTaken;
    private UserQuizState state;
    private ZonedDateTime completeDate;
    private Long quizGroupId;
    private String quizGroupName;
    private QuizLightResponse quiz;
    private List<Long> correctQuestionList;
    private List<UserWrongAnswerResponse> wrongQuestionList;
}
