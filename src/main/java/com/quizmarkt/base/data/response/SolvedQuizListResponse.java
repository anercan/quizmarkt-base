package com.quizmarkt.base.data.response;

import com.quizmarkt.base.data.enums.UserQuizState;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author anercan
 */

@Data
public class SolvedQuizListResponse {
    private Long id;
    private int timeTaken;
    private UserQuizState state;
    private ZonedDateTime completeDate;
    private Long quizGroupId;
    //private String quizGroupName;
    private LightQuizResponse quiz;
    private List<Long> correctQuestionList;
    private List<WrongAnswerResponse> wrongQuestionList;

    @Data
    public static class WrongAnswerResponse {
        private AnswerLightResponse wrongAnswer;
    }

    @Data
    public static class AnswerLightResponse {
        private Long id;
    }

    @Data
    public static class LightQuizResponse {
        private Long id;
        private String name;
        private Map<String, String> attributes;
        private int activeQuestionCount;
    }


}
