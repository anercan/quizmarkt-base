package com.quizmarkt.base.service;

import com.quizmarkt.base.data.enums.PremiumType;
import com.quizmarkt.base.data.request.GoogleLoginRequest;
import com.quizmarkt.base.data.request.PremiumInfoRequest;
import com.quizmarkt.base.data.request.SignInRequest;
import com.quizmarkt.base.data.response.SignInResponse;
import com.quizmarkt.base.data.response.UpdatePremiumInfoResponse;
import com.quizmarkt.base.manager.UserManagementManager;
import com.quizmarkt.base.manager.exception.CallWebServiceException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

/**
 * @author anercan
 */

@Service
@AllArgsConstructor
public class UserManagementService extends BaseService {

    private final UserManagementManager userManagementManager;

    public ResponseEntity<SignInResponse> signInWithMail(SignInRequest signInRequest) {
        if (StringUtils.isEmpty(signInRequest.getEmail()) || StringUtils.isEmpty(signInRequest.getPassword())) {
            return ResponseEntity.badRequest().build();
        }
        try {
            String jwt = userManagementManager.callBasicSignInService(signInRequest);
            if (StringUtils.isNotEmpty(jwt)) {
                return ResponseEntity.ok(SignInResponse.builder().jwt(jwt).build());
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (CallWebServiceException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<SignInResponse> signInWithGoogle(GoogleLoginRequest request) {
        String jwt = userManagementManager.googleSignIn(getGoogleLoginRequest(request.getToken(), request.getAppId()));
        if (StringUtils.isNotEmpty(jwt)) {
            return ResponseEntity.ok(SignInResponse.builder().jwt(jwt).build());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    private GoogleLoginRequest getGoogleLoginRequest(String token, int appId) {
        GoogleLoginRequest request = new GoogleLoginRequest();
        request.setExpirationDays(100L);
        request.setAppId(appId);
        request.setToken(token);
        return request;
    }

    public ResponseEntity<String> updatePremiumInfo() {
        PremiumInfoRequest premiumInfoRequest = new PremiumInfoRequest();
        premiumInfoRequest.setPremiumType(PremiumType.REGULAR_MONTHLY_PREMIUM);
        premiumInfoRequest.setExpireDate(ZonedDateTime.now().plusDays(31L));
        premiumInfoRequest.setUserId(getUserId());
        premiumInfoRequest.setJwtExpireDate(31L);
        UpdatePremiumInfoResponse updatePremiumInfoResponse = userManagementManager.setPremiumInfo(premiumInfoRequest);
        return ResponseEntity.ok(updatePremiumInfoResponse.getJwt());
    }
}
