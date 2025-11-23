package com.quizmarkt.base.data.cache;

import com.quizmarkt.base.data.entity.Quiz;
import com.quizmarkt.base.data.enums.UserQuizState;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author anercan
 */

@Getter
@AllArgsConstructor
public class UserQuizCacheable {
    private final Long id;
    private final String userId;
    private final UserQuizState state;
    private final ZonedDateTime startDate;
    private final ZonedDateTime completeDate;
    private final int appId;
    private final Long quizGroupId;
    private final Quiz quiz;
    private final List<Long> correctQuestionList = new ArrayList<>();
    private final List<UserWrongAnswerCacheable> wrongQuestionList = new ArrayList<>();
}
