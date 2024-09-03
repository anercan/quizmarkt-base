package com.quizmarkt.base.manager;

import com.quizmarkt.base.data.request.SignInRequest;
import com.quizmarkt.base.data.response.UserManagementSignInResponse;
import com.quizmarkt.base.manager.exception.CallWebServiceException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author anercan
 */

@AllArgsConstructor
@Service
public class UserManagementManager extends BaseManager {

    private final RestTemplate userManagementRestTemplate;
    @Value("${webService.userManagement.endpoint:https://localhost:8080}")
    private static String userManagementServiceEndpoint;

    public String callBasicSignInService(SignInRequest signInRequest) {
        try {
            String endpoint = userManagementServiceEndpoint + "/user-management/sign-in/basic";
            ResponseEntity<UserManagementSignInResponse> userManagementSignInResponse = userManagementRestTemplate.postForEntity(endpoint, signInRequest, UserManagementSignInResponse.class);
            if (userManagementSignInResponse.getStatusCode().is2xxSuccessful() && userManagementSignInResponse.getBody() != null) {
                return userManagementSignInResponse.getBody().getJwt();
            } else {
                logger.warn("callBasicSignInService has not succeed mail:{}", signInRequest.getMail());
                return null;
            }
        } catch (Exception e) {
            logger.error("callBasicSignInService failed for mail:{} with cause:", signInRequest.getMail(), e);
            throw new CallWebServiceException(e);
        }

    }
}
