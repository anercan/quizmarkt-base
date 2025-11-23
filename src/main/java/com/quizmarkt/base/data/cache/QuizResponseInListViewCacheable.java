package com.quizmarkt.base.data.cache;

import com.quizmarkt.base.data.enums.PremiumType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * @author anercan
 */
@Getter
@AllArgsConstructor
public class QuizResponseInListViewCacheable {
    private final Long id;
    private final String name;
    private final int priority;
    private final Map<String, String> attributes;
    private final List<PremiumType> availablePremiumTypes;
}
