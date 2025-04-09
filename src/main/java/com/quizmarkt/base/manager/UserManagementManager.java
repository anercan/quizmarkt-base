package com.quizmarkt.base.manager;

import com.quizmarkt.base.data.constant.CacheConstants;
import com.quizmarkt.base.data.request.GoogleLoginRequest;
import com.quizmarkt.base.data.request.PremiumInfoRequest;
import com.quizmarkt.base.data.request.SignInRequest;
import com.quizmarkt.base.data.request.UserFilterRequest;
import com.quizmarkt.base.data.response.UpdatePremiumInfoResponse;
import com.quizmarkt.base.data.response.UserInfo;
import com.quizmarkt.base.data.response.UserManagementSignInResponse;
import com.quizmarkt.base.data.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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
            ResponseEntity<UserManagementSignInResponse> response = userManagementRestTemplate.postForEntity(endpoint, googleLoginRequest, UserManagementSignInResponse.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody().getJwt();
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
            ResponseEntity<UpdatePremiumInfoResponse> response = userManagementRestTemplate.postForEntity(endpoint, premiumInfoRequest, UpdatePremiumInfoResponse.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            } else {
                logger.warn("googlePlaySubscribe has not succeed.");
                return null;
            }
        } catch (Exception e) {
            logger.error("googlePlaySubscribe failed for with cause:", e);
            return null;
        }
    }

    @Cacheable(value = CacheConstants.USER_INFO, key = "#userId", unless = "#result == null")
    public UserInfo getUserInfo(String userId) {
        try {
            String endpoint = userManagementServiceEndpoint + "/user-management/user-info/get-user-info";
            logger.info("getUserInfo will fetched from user management service id:{}", userId);
            ResponseEntity<UserInfo> response = userManagementRestTemplate.postForEntity(endpoint, userId, UserInfo.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            } else {
                logger.warn("getUserInfo has not succeed.");
                return null;
            }
        } catch (Exception e) {
            logger.error("getUserInfo failed for with cause:", e);
            return null;
        }
    }

    public String adminLogin(SignInRequest request) {
        try {
            String endpoint = userManagementServiceEndpoint + "/user-management/sign-in/admin";
            ResponseEntity<UserManagementSignInResponse> response = userManagementRestTemplate.postForEntity(endpoint, request, UserManagementSignInResponse.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody().getJwt();
            } else {
                logger.warn("adminLogin has not succeed token:{}", request.getEmail());
                return null;
            }
        } catch (Exception e) {
            logger.error("adminLogin failed for username:{} with cause:", request.getEmail());
            return null;
        }
    }

    public List<UserResponse> getFilteredUsers(UserFilterRequest request) {
        try {
            String endpoint = userManagementServiceEndpoint + "/user-management/user-info/get-users-filter";
            logger.info("getFilteredUsers will fetched from user management service id:{}", request);
            ResponseEntity<List<UserResponse>> response = userManagementRestTemplate.exchange(endpoint, HttpMethod.POST, new HttpEntity<>(request), new ParameterizedTypeReference<>() {});
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            } else {
                logger.warn("getFilteredUsers has not succeed.");
                return null;
            }
        } catch (Exception e) {
            logger.error("getFilteredUsers failed for with cause:", e);
            return null;
        }
    }
}
