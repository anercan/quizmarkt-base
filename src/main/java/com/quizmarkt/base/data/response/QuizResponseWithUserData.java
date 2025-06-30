package com.quizmarkt.base.data.response;

import com.quizmarkt.base.data.enums.PremiumType;
import com.quizmarkt.base.data.enums.UserQuizState;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author anercan
 */
@Data
public class QuizResponseWithUserData {

    private Long id;
    private String name;
    private int priority;
    private Map<String, String> attributes;
    int solvedCount;
    int correctCount;
    int questionCount;
    private UserQuizState state;
    private boolean isLocked;
    private List<PremiumType> availablePremiumTypes;

}
