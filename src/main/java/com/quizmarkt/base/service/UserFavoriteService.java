package com.quizmarkt.base.service;

import com.quizmarkt.base.data.entity.Question;
import com.quizmarkt.base.data.mapper.QuestionMapper;
import com.quizmarkt.base.data.request.AddOrRemoveFavoriteRequest;
import com.quizmarkt.base.data.response.ApiResponse;
import com.quizmarkt.base.data.response.FavoriteQuestionIdsResponse;
import com.quizmarkt.base.data.response.QuestionResponse;
import com.quizmarkt.base.data.response.QuizResponseForFavorites;
import com.quizmarkt.base.manager.CacheProviderManager;
import com.quizmarkt.base.manager.QuestionManager;
import com.quizmarkt.base.manager.UserFavoriteManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author anercan
 */

@Service
@AllArgsConstructor
public class UserFavoriteService extends BaseService {

    private final UserFavoriteManager userFavoriteManager;
    private final QuestionMapper questionMapper;
    private final QuestionManager questionManager;
    private final CacheProviderManager cacheProviderManager;

    public ApiResponse<QuizResponseForFavorites> getUserFavoriteQuestions() {
        List<Long> userFavoriteQuestionIds = userFavoriteManager.getUserFavoriteQuestionIds(getUserId());
        List<Question> questionsWithIdList = questionManager.getQuestionsWithIdListWithOrder(userFavoriteQuestionIds);
        List<QuestionResponse> questionResponses = questionMapper.toQuestionListResponse(questionsWithIdList);
        return new ApiResponse<>(QuizResponseForFavorites.builder().name("Favorites").questionList(questionResponses).build());
    }

    public ApiResponse<FavoriteQuestionIdsResponse> addOrRemoveFavorite(AddOrRemoveFavoriteRequest request) {
        cacheProviderManager.evictUserFavoritesCache(getUserId());
        if (request.isAdd()) {
            return new ApiResponse<>(userFavoriteManager.addToFavorite(request.getQuestionId()));
        } else {
            return new ApiResponse<>(userFavoriteManager.removeFavorite(request.getQuestionId()));
        }
    }

    public ApiResponse<FavoriteQuestionIdsResponse> getUserFavoritesQuestionIds() {
        List<Long> userFavoriteQuestionIds = userFavoriteManager.getUserFavoriteQuestionIds(getUserId());
        return new ApiResponse<>(FavoriteQuestionIdsResponse.builder().favoriteIds(userFavoriteQuestionIds).build());
    }
}
