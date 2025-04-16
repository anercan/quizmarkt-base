package com.quizmarkt.base.data.response;

import com.quizmarkt.base.data.enums.UserQuizState;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author anercan
 */

@Data
public class UserDailyQuizResponse {
    private UserQuizState state;
    private LocalDate createdDate;
    private List<Long> correctQuestionIdList = new ArrayList<>();
    private List<Long> wrongQuestionIdList = new ArrayList<>();
    private Map<String,Long> wrongQuestionsSubjects = new HashMap<>();
}
