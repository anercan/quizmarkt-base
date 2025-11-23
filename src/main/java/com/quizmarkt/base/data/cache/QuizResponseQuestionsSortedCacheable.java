package com.quizmarkt.base.data.cache;

import com.quizmarkt.base.data.enums.PremiumType;
import com.quizmarkt.base.data.response.QuestionResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * @author anercan
 */

@EqualsAndHashCode(callSuper = false)
@Getter
@AllArgsConstructor
@Builder
public class QuizResponseQuestionsSortedCacheable {
    private final Long id;
    private final String name;
    private final Map<String, String> attributes;
    private final List<QuestionResponse> questionList;
    private final List<PremiumType> availablePremiumTypes;
}
