package com.quesmarkt.quesmarktbase.util;

import com.quesmarkt.quesmarktbase.data.entity.UserQuiz;

/**
 * @author anercan
 */
public class UserQuizUtil {

    public static int getSolvedQuestionDataOfUserQuiz(UserQuiz userQuiz) { //todo kullanıldığı yer graph yapılabilir
        return userQuiz.getCorrectQuestionList().size() + userQuiz.getWrongQuestionList().size();     //todo research hibernate.default_batch_fetch_size
    }

    public static boolean isQuizCompleted(UserQuiz userQuiz) { //todo  count select yapılmalı! 5 sorgu
        return userQuiz.getWrongQuestionList().size() + userQuiz.getCorrectQuestionList().size() == userQuiz.getQuiz().getQuestionList().size();
    }

}
