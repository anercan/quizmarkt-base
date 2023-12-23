package com.quesmarkt.quesmarktbase.data.mapper;

import com.quesmarkt.quesmarktbase.data.entity.Quiz;
import com.quesmarkt.quesmarktbase.data.entity.UserQuiz;
import com.quesmarkt.quesmarktbase.data.response.QuizResponse;
import com.quesmarkt.quesmarktbase.data.response.QuizResponseWithUserData;
import org.mapstruct.Mapper;

import java.util.Map;

import static com.quesmarkt.quesmarktbase.util.UserQuizUtil.getSolvedQuestionDataOfUserQuiz;

/**
 * @author anercan
 */

@Mapper(componentModel = "spring")
public interface QuizMapper {
    QuizResponseWithUserData toQuizResponseWithUserData(Quiz quiz);

    default QuizResponseWithUserData getQuizResponseWithUserData(Map<Long, UserQuiz> quizIdUserQuizMap, Quiz quiz) {
        QuizResponseWithUserData quizGroupWithUserData = this.toQuizResponseWithUserData(quiz);
        UserQuiz userQuiz = quizIdUserQuizMap.get(quiz.getId());
        if (userQuiz != null) {
            quizGroupWithUserData.setSolvedCount(getSolvedQuestionDataOfUserQuiz(userQuiz));
        } else {
            quizGroupWithUserData.setSolvedCount(0);
        }
        quizGroupWithUserData.setQuestionCount(quiz.getQuestionList().size()); //todo 3 sorgu atıyor kesin count sorgusu yaz
        return quizGroupWithUserData;
    }

    QuizResponse toQuizResponse(Quiz quiz);
}
