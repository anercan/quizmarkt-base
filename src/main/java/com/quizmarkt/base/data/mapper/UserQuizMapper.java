package com.quizmarkt.base.data.mapper;

import com.quizmarkt.base.data.entity.Answer;
import com.quizmarkt.base.data.entity.Question;
import com.quizmarkt.base.data.entity.UserQuiz;
import com.quizmarkt.base.data.entity.UserWrongAnswer;
import com.quizmarkt.base.data.request.CreateUpdateUserQuizRequest;
import com.quizmarkt.base.data.response.SolvedQuizListResponse;
import com.quizmarkt.base.data.response.UserQuizResponse;
import com.quizmarkt.base.data.response.UserWrongAnswerResponse;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author anercan
 */

@Mapper(componentModel = "spring")
public interface UserQuizMapper {

    UserQuizResponse toUserQuizResponse(UserQuiz userQuiz);

    SolvedQuizListResponse toUserQuizListResponse(UserQuiz userQuiz);

    List<UserWrongAnswerResponse> toListUserWrongAnswerResponse(List<UserWrongAnswer> userWrongAnswerList);

    default UserWrongAnswer getUserWrongAnswerFromCreateOrUpdateRequest(CreateUpdateUserQuizRequest request) {
        UserWrongAnswer userWrongAnswer = new UserWrongAnswer();
        Question question = new Question();
        question.setId(request.getUserWrongAnswerRequest().getQuestionId());
        userWrongAnswer.setQuestion(question);
        Answer wrongAnswer = new Answer();
        wrongAnswer.setId(request.getUserWrongAnswerRequest().getAnswerId());
        userWrongAnswer.setWrongAnswer(wrongAnswer);
        return userWrongAnswer;
    }
}
