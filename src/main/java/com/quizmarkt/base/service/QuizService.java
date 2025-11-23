package com.quizmarkt.base.service;

import com.quizmarkt.base.data.cache.QuizResponseInListViewCacheable;
import com.quizmarkt.base.data.cache.QuizResponseQuestionsSortedCacheable;
import com.quizmarkt.base.data.entity.UserQuiz;
import com.quizmarkt.base.data.mapper.QuizMapper;
import com.quizmarkt.base.data.mapper.UserQuizMapper;
import com.quizmarkt.base.data.request.QuizListWithUserDataRequest;
import com.quizmarkt.base.data.response.ApiResponse;
import com.quizmarkt.base.data.response.QuizListResponse;
import com.quizmarkt.base.data.response.QuizResponseWithUserData;
import com.quizmarkt.base.data.response.QuizResponseWithUserQuizData;
import com.quizmarkt.base.manager.QuizManager;
import com.quizmarkt.base.manager.UserQuizManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author anercan
 */

@Service
@AllArgsConstructor
public class QuizService extends BaseService {

    private final QuizManager quizManager;
    private final UserQuizManager userQuizManager;
    private final QuizMapper quizMapper;
    private final UserQuizMapper userQuizMapper;

    public ApiResponse<QuizListResponse> getQuizListWithUserData(QuizListWithUserDataRequest request) {
        List<QuizResponseInListViewCacheable> quizResponseInListViewListCacheable = quizManager.getQuizResponseWithUserDataList(request);
        Map<Long, UserQuiz> quizIdUserQuizMap = userQuizManager.getQuizIdAndSolvedQuestionCountMap(request.getQuizGroupId());
        List<QuizResponseWithUserData> quizWithUserDataList = quizResponseInListViewListCacheable
                .stream()
                .map(quizResponseInListViewCacheable -> quizMapper.getQuizResponseWithUserData(quizIdUserQuizMap.get(quizResponseInListViewCacheable.getId()), !quizResponseInListViewCacheable.getAvailablePremiumTypes().contains(getPremiumType())))
                .collect(Collectors.toList());
        return new ApiResponse<>(QuizListResponse.builder().quizResponseWithUserDataList(quizWithUserDataList).build());
    }

    public ApiResponse<QuizResponseWithUserQuizData> getQuizWithUserQuizData(Long quizId) {
        QuizResponseQuestionsSortedCacheable quizResponseQuestionsSortedCacheable = quizManager.getQuizWithIdSorted(quizId);
        if (Objects.isNull(quizResponseQuestionsSortedCacheable)) {
            return new ApiResponse<>(ApiResponse.Status.fail());
        }
        if (!quizResponseQuestionsSortedCacheable.getAvailablePremiumTypes().contains(getPremiumType())) {
            logger.warn("getQuizWithUserQuizData without correct premium info.userId:{} quizId:{}", getUserId(), quizId);
            return new ApiResponse<>(ApiResponse.Status.notAuthorizedPremiumOperation("getQuizListWithUserData"));
        }
        Optional<UserQuiz> userQuizOptional = userQuizManager.getUserQuizWithQuizIdAndUserId(quizId);
        QuizResponseWithUserQuizData quizResponseWithUserQuizData = quizMapper.toQuizResponseWithUserQuizData(quizResponseQuestionsSortedCacheable);
        userQuizOptional.ifPresent(userQuiz -> quizResponseWithUserQuizData.setUserQuiz(userQuizMapper.toUserQuizResponse(userQuiz)));
        return new ApiResponse<>(quizResponseWithUserQuizData);
    }

}
