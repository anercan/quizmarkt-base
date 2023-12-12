package com.quesmarkt.quesmarktbase.util;

import com.quesmarkt.quesmarktbase.data.entity.UserQuiz;

/**
 * @author anercan
 */
public class UserQuizUtil {

    public static int getSolvedQuestionDataOfUserQuiz(UserQuiz userQuiz) { //todo kullanıldığı yer graph yapılabilir
        return userQuiz.getCorrectAnswers().size() + userQuiz.getWrongAnswers().size();
    }
}
