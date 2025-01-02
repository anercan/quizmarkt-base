package com.quizmarkt.base.service;

import com.quizmarkt.base.data.response.UserDataResponse;
import com.quizmarkt.base.data.response.UserInfo;
import com.quizmarkt.base.manager.UserManagementManager;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author anercan
 */

@Service
@AllArgsConstructor
public class ProfileService extends BaseService {

    private final UserDataService userDataService;
    private final UserManagementManager userManagementManager;

    public ResponseEntity<UserDataResponse> getUserData() {
        Optional<UserInfo> userInfo = getUserInfo();
        if (isRegularPremium()) {
            return ResponseEntity.ok(userDataService.getPremiumUserData(userInfo, getUserId(), getAppId(), isRegularPremium()));
        } else {
            return ResponseEntity.ok(userDataService.getNonPremiumUserData(userInfo, getUserId(), getAppId(), isRegularPremium()));
        }
    }

    private Optional<UserInfo> getUserInfo() {
        UserInfo userInfo = userManagementManager.getUserInfo(getUserId());
        if (userInfo != null) {
            return Optional.of(userInfo);
        }
        return Optional.empty();
    }
}
