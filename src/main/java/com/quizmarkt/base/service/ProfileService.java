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
        return new ApiResponse<>(userDataService.getPremiumUserData(userInfo, getUserId()));
    }

    private Optional<UserInfo> getUserInfo() {
        UserInfo userInfo = userManagementManager.getUserInfo(getUserId());
        if (userInfo != null) {
            return Optional.of(userInfo);
        }
        return Optional.empty();
    }

    public ApiResponse<UserQuizAnalyseResponse> getUserQuizAnalyses() {
        List<UserQuiz> userQuizList = userQuizManager.getUserQuizList(getUserId());
        return new ApiResponse<>(UserQuizAnalyseResponse.builder().wrongsMap(userDataService.getWrongsInfo(userQuizList)).build());
    }

    public ApiResponse<UserActivityData> getUserActivityData() {
        List<UserQuiz> userQuizList = userQuizManager.getUserQuizList(getUserId());
        return new ApiResponse<>(UserActivityData.builder().activityDataList(userDataService.getActivityData(userQuizList)).build());
    }
}
