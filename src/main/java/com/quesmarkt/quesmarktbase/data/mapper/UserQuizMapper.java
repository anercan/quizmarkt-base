package com.quesmarkt.quesmarktbase.data.mapper;

import com.quesmarkt.quesmarktbase.data.entity.Answers;
import com.quesmarkt.quesmarktbase.data.entity.Question;
import com.quesmarkt.quesmarktbase.data.entity.UserQuiz;
import com.quesmarkt.quesmarktbase.data.entity.UserWrongAnswer;
import com.quesmarkt.quesmarktbase.data.request.CreateUpdateUserQuizRequest;
import com.quesmarkt.quesmarktbase.data.response.UserQuizInListResponse;
import com.quesmarkt.quesmarktbase.data.response.UserQuizResponse;
import com.quesmarkt.quesmarktbase.data.response.UserWrongAnswerResponse;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author anercan
 */

@Mapper(componentModel = "spring")
public interface UserQuizMapper {

    UserQuizResponse toUserQuizResponse(UserQuiz userQuiz);

    List<UserQuizInListResponse> toUserQuizListResponse(List<UserQuiz> userQuiz);

    List<UserWrongAnswerResponse> toListUserWrongAnswerResponse(List<UserWrongAnswer> userWrongAnswerList);

    default UserWrongAnswer getUserWrongAnswerFromCreateOrUpdateRequest(CreateUpdateUserQuizRequest request) {
        UserWrongAnswer userWrongAnswer = new UserWrongAnswer();
        Question question = new Question();
        question.setId(request.getUserWrongAnswerRequest().getQuestionId());
        userWrongAnswer.setQuestion(question);
        Answers wrongAnswer = new Answers();
        wrongAnswer.setId(request.getUserWrongAnswerRequest().getAnswerId());
        userWrongAnswer.setWrongAnswer(wrongAnswer);
        return userWrongAnswer;
    }
}
