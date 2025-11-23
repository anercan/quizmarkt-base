package com.quizmarkt.base.data.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author anercan
 */

@Getter
@AllArgsConstructor
public class UserWrongAnswerCacheable {
    private final Long id;
    private final QuestionCacheable question;
    private final AnswerCacheable wrongAnswer;
}
