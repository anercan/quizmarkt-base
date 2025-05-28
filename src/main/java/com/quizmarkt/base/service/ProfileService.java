package com.quizmarkt.base.service;

import com.quizmarkt.base.data.entity.UserQuiz;
import com.quizmarkt.base.data.response.*;
import com.quizmarkt.base.manager.UserManagementManager;
import com.quizmarkt.base.manager.UserQuizManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author anercan
 */

@Service
@AllArgsConstructor
public class ProfileService extends BaseService {

    private final UserDataService userDataService;
    private final UserManagementManager userManagementManager;
    private final UserQuizManager userQuizManager;

    public ApiResponse<UserDataResponse> getUserData() {
        Optional<UserInfo> userInfo = getUserInfo();
        if (isRegularPremium()) {
            return new ApiResponse<>(userDataService.getPremiumUserData(userInfo, getUserId(), getAppId(), isRegularPremium()));
        } else {
            return new ApiResponse<>(userDataService.getNonPremiumUserData(userInfo, getUserId(), getAppId(), isRegularPremium()));
        }
    }

    private Optional<UserInfo> getUserInfo() {
        UserInfo userInfo = userManagementManager.getUserInfo(getUserId());
        if (userInfo != null) {
            return Optional.of(userInfo);
        }
        return Optional.empty();
    }

    public ApiResponse<UserQuizAnalyseResponse> getUserQuizAnalyses() {
        if (isRegularPremium()) {
            List<UserQuiz> userQuizList = userQuizManager.getUserQuizList(getUserId());
            return new ApiResponse<>(UserQuizAnalyseResponse.builder().wrongsMap(userDataService.getWrongsInfo(userQuizList)).build());
        } else {
            return new ApiResponse<>(ApiResponse.Status.notAuthorizedPremiumOperation("getUserQuizAnalyses"));
        }
    }

    public ApiResponse<UserActivityData> getUserActivityData() {
        if (isRegularPremium()) {
            List<UserQuiz> userQuizList = userQuizManager.getUserQuizList(getUserId());
            return new ApiResponse<>(UserActivityData.builder().activityDataList(userDataService.getActivityData(userQuizList)).build());
        } else {
            return new ApiResponse<>(ApiResponse.Status.notAuthorizedPremiumOperation("getUserActivityData"));
        }
    }
}
