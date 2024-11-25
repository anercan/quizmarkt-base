package com.quizmarkt.base.manager;

import com.quizmarkt.base.data.request.GoogleLoginRequest;
import com.quizmarkt.base.data.request.PremiumInfoRequest;
import com.quizmarkt.base.data.response.UpdatePremiumInfoResponse;
import com.quizmarkt.base.data.response.UserInfo;
import com.quizmarkt.base.data.response.UserManagementSignInResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author anercan
 */

@RequiredArgsConstructor
@Service
public class UserManagementManager extends BaseManager {

    private final RestTemplate userManagementRestTemplate;
    @Value("${webService.userManagement.endpoint}")
    private String userManagementServiceEndpoint;

    public String googleSignIn(GoogleLoginRequest googleLoginRequest) {
        try {
            String endpoint = userManagementServiceEndpoint + "/user-management/sign-in/google-auth";
            ResponseEntity<UserManagementSignInResponse> userManagementSignInResponse = userManagementRestTemplate.postForEntity(endpoint, googleLoginRequest, UserManagementSignInResponse.class);
            if (userManagementSignInResponse.getStatusCode().is2xxSuccessful() && userManagementSignInResponse.getBody() != null) {
                return userManagementSignInResponse.getBody().getJwt();
            } else {
                logger.warn("callBasicSignInService has not succeed token:{}", googleLoginRequest.getToken());
                return null;
            }
        } catch (Exception e) {
            logger.error("callBasicSignInService failed for token:{} with cause:", googleLoginRequest.getToken(), e);
            return null;
        }
    }

    public UpdatePremiumInfoResponse googlePlaySubscribe(PremiumInfoRequest premiumInfoRequest) {
        try {
            String endpoint = userManagementServiceEndpoint + "/user-management/user-info/google-play-subscribe";
            ResponseEntity<UpdatePremiumInfoResponse> updatePremiumInfoResponse = userManagementRestTemplate.postForEntity(endpoint, premiumInfoRequest, UpdatePremiumInfoResponse.class);
            if (updatePremiumInfoResponse.getStatusCode().is2xxSuccessful() && updatePremiumInfoResponse.getBody() != null) {
                return updatePremiumInfoResponse.getBody();
            } else {
                logger.warn("googlePlaySubscribe has not succeed.");
                return null;
            }
        } catch (Exception e) {
            logger.error("googlePlaySubscribe failed for with cause:", e);
            return null;
        }
    }

    public UserInfo getUserInfo(String userId) {
        try {
            String endpoint = userManagementServiceEndpoint + "/user-management/user-info/get-user-info";
            ResponseEntity<UserInfo> userInfoResponseEntity = userManagementRestTemplate.postForEntity(endpoint, userId, UserInfo.class);
            if (userInfoResponseEntity.getStatusCode().is2xxSuccessful() && userInfoResponseEntity.getBody() != null) {
                return userInfoResponseEntity.getBody();
            } else {
                logger.warn("getUserInfo has not succeed.");
                return null;
            }
        } catch (Exception e) {
            logger.error("getUserInfo failed for with cause:", e);
            return null;
        }
    }
}
