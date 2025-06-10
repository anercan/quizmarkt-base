package com.quizmarkt.base.service;

import com.quizmarkt.base.data.entity.UserQuiz;
import com.quizmarkt.base.data.mapper.QuizMapper;
import com.quizmarkt.base.data.mapper.UserQuizMapper;
import com.quizmarkt.base.data.request.QuizListWithUserDataRequest;
import com.quizmarkt.base.data.response.ApiResponse;
import com.quizmarkt.base.data.response.QuizListResponse;
import com.quizmarkt.base.data.response.QuizResponse;
import com.quizmarkt.base.data.response.QuizResponseWithUserData;
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

    //@Transactional(readOnly = true) todo 2025-06-05T03:20:25.618Z WARN  HHH90003004: firstResult/maxResults specified with collection fetch; applying in memory check if its giving that warning
    public ApiResponse<QuizListResponse> getQuizListWithUserData(QuizListWithUserDataRequest request) {
        List<QuizResponseWithUserData> quizResponseWithUserDataList = quizManager.getQuizResponseWithUserDataList(request);
        Map<Long, UserQuiz> quizIdUserQuizMap = userQuizManager.getQuizIdAndSolvedQuestionCountMap(request.getQuizGroupId());
        List<QuizResponseWithUserData> quizWithUserDataList = quizResponseWithUserDataList.stream().map(quizWithUserData -> quizMapper.getQuizResponseWithUserData(quizIdUserQuizMap, getPremiumType(), quizWithUserData)).collect(Collectors.toList());
        return new ApiResponse<>(QuizListResponse.builder().quizResponseWithUserDataList(quizWithUserDataList).build());
    }

    public ApiResponse<QuizResponse> getQuizWithUserQuizDataForStartTest(Long quizId) {
        QuizResponse quizResponse = quizManager.getQuizResponseWithId(quizId);
        if (Objects.isNull(quizResponse)) {
            return new ApiResponse<>(ApiResponse.Status.fail());
        }
        if (!quizResponse.getAvailablePremiumTypes().contains(getPremiumType())) {
            logger.warn("getQuizWithUserQuizDataForStartTest without correct premium info.userId:{} quizId:{}", getUserId(), quizId);
            return new ApiResponse<>(ApiResponse.Status.notAuthorizedPremiumOperation("getQuizListWithUserData"));
        }
        Optional<UserQuiz> userQuizOptional = userQuizManager.getUserQuizWithQuizIdAndUserId(quizId);
        userQuizOptional.ifPresent(userQuiz -> quizResponse.setUserQuiz(userQuizMapper.toUserQuizResponse(userQuiz)));
        return new ApiResponse<>(quizResponse);
    }

}
