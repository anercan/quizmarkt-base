package com.quizmarkt.base.manager;

import com.quizmarkt.base.data.request.GoogleLoginRequest;
import com.quizmarkt.base.data.request.PremiumInfoRequest;
import com.quizmarkt.base.data.request.SignInRequest;
import com.quizmarkt.base.data.response.UpdatePremiumInfoResponse;
import com.quizmarkt.base.data.response.UserManagementSignInResponse;
import com.quizmarkt.base.manager.exception.CallWebServiceException;
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
    @Value("${webService.userManagement.endpoint:http://localhost:9091}")
    private String userManagementServiceEndpoint;

    public String callBasicSignInService(SignInRequest signInRequest) {
        try {
            String endpoint = userManagementServiceEndpoint + "/user-management/sign-in/basic";
            ResponseEntity<UserManagementSignInResponse> userManagementSignInResponse = userManagementRestTemplate.postForEntity(endpoint, signInRequest, UserManagementSignInResponse.class);
            if (userManagementSignInResponse.getStatusCode().is2xxSuccessful() && userManagementSignInResponse.getBody() != null) {
                return userManagementSignInResponse.getBody().getJwt();
            } else {
                logger.warn("callBasicSignInService has not succeed mail:{}", signInRequest.getEmail());
                return null;
            }
        } catch (Exception e) {
            logger.error("callBasicSignInService failed for mail:{} with cause:", signInRequest.getEmail(), e);
            throw new CallWebServiceException(e);
        }
    }

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
            throw new CallWebServiceException(e);
        }
    }

    public UpdatePremiumInfoResponse setPremiumInfo(PremiumInfoRequest premiumInfoRequest) {
        try {
            String endpoint = userManagementServiceEndpoint + "/user-management/user-info/update-premium-info";
            ResponseEntity<UpdatePremiumInfoResponse> updatePremiumInfoResponse = userManagementRestTemplate.postForEntity(endpoint, premiumInfoRequest, UpdatePremiumInfoResponse.class);
            if (updatePremiumInfoResponse.getStatusCode().is2xxSuccessful() && updatePremiumInfoResponse.getBody() != null) {
                return updatePremiumInfoResponse.getBody();
            } else {
                logger.warn("setPremiumInfo has not succeed.");
                return null;
            }
        } catch (Exception e) {
            logger.error("setPremiumInfo failed for with cause:", e);
            throw new CallWebServiceException(e);
        }
    }
}
