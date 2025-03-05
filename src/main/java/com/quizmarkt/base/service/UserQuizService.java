package com.quizmarkt.base.service;

import com.quizmarkt.base.data.entity.UserQuiz;
import com.quizmarkt.base.data.mapper.UserQuizMapper;
import com.quizmarkt.base.data.request.CreateUpdateUserQuizRequest;
import com.quizmarkt.base.data.response.*;
import com.quizmarkt.base.manager.UserQuizManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author anercan
 */

@Service
@AllArgsConstructor
public class UserQuizService extends BaseService {

    private final UserQuizManager userQuizManager;
    private final UserQuizMapper userQuizMapper;

    public ApiResponse<UserQuizListResponse> getUserQuizList() {
        List<SolvedQuizListResponse> userQuizInListResponses = new ArrayList<>();
        List<UserQuiz> userQuizList = userQuizManager.getOrderedUserQuizList();
        for (UserQuiz userQuiz : userQuizList) {
            SolvedQuizListResponse userQuizInListResponse = userQuizMapper.toUserQuizListResponse(userQuiz);
            //userQuizInListResponse.setQuizGroupName(getQuizGroupNameFromUserQuiz(userQuiz, quizGroupId));
            userQuizInListResponses.add(userQuizInListResponse);
        }
        return new ApiResponse<>(UserQuizListResponse.builder()
                .userQuizResponseList(userQuizInListResponses)
                .build());
    }

    public ApiResponse<BooleanResponse> createUpdateUserQuiz(CreateUpdateUserQuizRequest request) {
        Optional<UserQuiz> optionalUserQuiz = userQuizManager.getUserQuizWithQuizIdAndUserId(request.getQuizId());
        if (optionalUserQuiz.isEmpty()) {
            UserQuiz userQuiz = userQuizManager.createNewUserQuiz(request);
            return new ApiResponse<>(BooleanResponse.builder().value(Objects.nonNull(userQuiz)).build());
        } else {
            UserQuiz userQuiz = userQuizManager.updateUserQuiz(request, optionalUserQuiz.get());
            return new ApiResponse<>(BooleanResponse.builder().value(Objects.nonNull(userQuiz)).build());
        }
    }

    public ApiResponse<CompletedStaticsResponse> getCompletedQuizStatics(Long quizId) {
        try {
            int better = 0;
            int worse = 0;
            int equal = 0;
            Optional<UserQuiz> completedUserQuizOpt = userQuizManager.getUserQuizWithQuizIdAndUserIdCompleted(quizId);
            if (completedUserQuizOpt.isPresent()) {
                List<UserQuiz> otherUserQuizzes = userQuizManager.getUserQuizWithQuizId(quizId);
                for (UserQuiz otherUserQuiz : otherUserQuizzes) {
                    if (otherUserQuiz.getWrongQuestionList().size() > completedUserQuizOpt.get().getWrongQuestionList().size()) {
                        better++;
                    } else if (otherUserQuiz.getWrongQuestionList().size() == completedUserQuizOpt.get().getWrongQuestionList().size()) {
                        equal++;
                    } else if (otherUserQuiz.getWrongQuestionList().size() < completedUserQuizOpt.get().getWrongQuestionList().size()) {
                        worse++;
                    }
                }
                return new ApiResponse<>(CompletedStaticsResponse.builder().betterCount(better).equalCount(equal).worseCount(worse).build());
            }
        } catch (Exception e) {
            logger.error("getCompletedQuizStatics got exception", e);
            return new ApiResponse<>(ApiResponse.Status.fail());
        }
        return new ApiResponse<>(ApiResponse.Status.fail());
    }
}
