package com.quizmarkt.base.service;

import com.quizmarkt.base.data.cache.UserDataResponse;
import com.quizmarkt.base.data.cache.UserQuizCacheable;
import com.quizmarkt.base.data.response.ApiResponse;
import com.quizmarkt.base.data.response.UserActivityData;
import com.quizmarkt.base.data.response.UserInfo;
import com.quizmarkt.base.data.response.UserQuizAnalyseResponse;
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
public class ProfileService extends BaseService { // todo user Quize domainine tasinabilir

    private final UserDataService userDataService;
    private final UserManagementManager userManagementManager;
    private final UserQuizManager userQuizManager;

    public ApiResponse<UserDataResponse> getUserData() {
        Optional<UserInfo> userInfo = getUserInfo();
        return new ApiResponse<>(userDataService.getUserData(userInfo, getUserId()));
    }

    private Optional<UserInfo> getUserInfo() {
        UserInfo userInfo = userManagementManager.getUserInfo(getUserId());
        if (userInfo != null) {
            return Optional.of(userInfo);
        }
        return Optional.empty();
    }

    public ApiResponse<UserQuizAnalyseResponse> getUserQuizAnalyses() {
        List<UserQuizCacheable> userQuizList = userQuizManager.getUserQuizList(getUserId());
        return new ApiResponse<>(UserQuizAnalyseResponse.builder().wrongsMap(userDataService.getWrongsInfo(userQuizList)).build());
    }

    public ApiResponse<UserActivityData> getUserActivityData() {
        List<UserQuizCacheable> userQuizList = userQuizManager.getUserQuizList(getUserId());
        return new ApiResponse<>(UserActivityData.builder().activityDataList(userDataService.getActivityData(userQuizList)).build());
    }
}
