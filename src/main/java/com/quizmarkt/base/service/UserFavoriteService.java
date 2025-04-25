package com.quizmarkt.base.service;

import com.quizmarkt.base.data.entity.Question;
import com.quizmarkt.base.data.mapper.QuestionMapper;
import com.quizmarkt.base.data.request.AddOrRemoveFavoriteRequest;
import com.quizmarkt.base.data.response.ApiResponse;
import com.quizmarkt.base.data.response.QuestionResponse;
import com.quizmarkt.base.manager.QuestionManager;
import com.quizmarkt.base.manager.UserFavoriteManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author anercan
 */

@Service
@AllArgsConstructor
public class UserFavoriteService extends BaseService {

    private final UserFavoriteManager userFavoriteManager;
    private final QuestionMapper questionMapper;
    private final QuestionManager questionManager;

    public ApiResponse<List<QuestionResponse>> getUserFavorites() {
        Set<Long> userFavoriteQuestionIds = userFavoriteManager.getUserFavoriteQuestionIds();
        List<Question> questionsWithIdList = questionManager.getQuestionsWithIdList(userFavoriteQuestionIds);
        List<QuestionResponse> questionResponses = questionMapper.toQuestionListResponse(questionsWithIdList);
        return new ApiResponse<>(questionResponses);
    }

    public ApiResponse<Boolean> addOrRemoveFavorite(AddOrRemoveFavoriteRequest request) {
        if (request.isAdd()) {
            userFavoriteManager.addToFavorite(request.getQuestionId());
        } else {
            userFavoriteManager.removeFavorite(request.getQuestionId());
        }
        return new ApiResponse<>(Boolean.TRUE);
    }

}
