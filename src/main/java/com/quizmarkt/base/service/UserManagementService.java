package com.quizmarkt.base.service;

import com.quizmarkt.base.data.enums.PremiumType;
import com.quizmarkt.base.data.request.*;
import com.quizmarkt.base.data.response.ApiResponse;
import com.quizmarkt.base.data.response.JwtResponse;
import com.quizmarkt.base.data.response.UpdatePremiumInfoResponse;
import com.quizmarkt.base.manager.UserManagementManager;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

/**
 * @author anercan
 */

@Service
@AllArgsConstructor
public class UserManagementService extends BaseService {

    private final UserManagementManager userManagementManager;

    public ApiResponse<JwtResponse> signInWithGoogle(GoogleLoginRequest request) {
        request.setExpirationDate(Date.from(LocalDate.now().plusDays(28).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        String jwt = userManagementManager.googleSignIn(request);
        if (StringUtils.isNotEmpty(jwt)) {
            return new ApiResponse<>(JwtResponse.builder().jwt(jwt).build());
        } else {
            return new ApiResponse<>(ApiResponse.Status.fail("Login failed!"));
        }
    }

    public ApiResponse<JwtResponse> signInWithApple(AppleLoginRequest request) {
        request.setExpirationDate(Date.from(LocalDate.now().plusDays(28).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        String jwt = userManagementManager.appleSignIn(request);
        if (StringUtils.isNotEmpty(jwt)) {
            return new ApiResponse<>(JwtResponse.builder().jwt(jwt).build());
        } else {
            return new ApiResponse<>(ApiResponse.Status.fail("Login failed!"));
        }
    }

    public ResponseEntity<JwtResponse> adminLogin(SignInRequest request) {
        HashMap<String, String> jwtClaims = new HashMap<>();
        jwtClaims.put("ROLE", "ADMIN");
        request.setJwtClaims(jwtClaims);
        String jwt = userManagementManager.adminLogin(request);
        if (StringUtils.isNotEmpty(jwt)) {
            return ResponseEntity.ok(JwtResponse.builder().jwt(jwt).build());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    public ApiResponse<JwtResponse> googlePlaySubscribe(GoogleSubscriptionRequest googleSubscriptionRequest) {
        PremiumInfoRequest premiumInfoRequest = new PremiumInfoRequest();
        premiumInfoRequest.setGoogleSubscriptionRequest(googleSubscriptionRequest);
        premiumInfoRequest.setPremiumType(PremiumType.LEVEL1);
        premiumInfoRequest.setUserId(getUserId());
        premiumInfoRequest.setAppId(getAppId());
        try {
            UpdatePremiumInfoResponse updatePremiumInfoResponse = userManagementManager.googlePlaySubscribe(premiumInfoRequest);
            if (updatePremiumInfoResponse != null && updatePremiumInfoResponse.isSucceed()) {
                return new ApiResponse<>(JwtResponse.builder().jwt(updatePremiumInfoResponse.getJwt()).build());
            }
            logger.warn("googlePlaySubscribe returned fail from userManagementManager message:{} userId:{}", updatePremiumInfoResponse.getMessage() != null ? updatePremiumInfoResponse.getMessage() : "", getUserId());
            return new ApiResponse<>(ApiResponse.Status.fail("Failed Subscription"));
        } catch (Exception e) {
            logger.error("googlePlaySubscribe got exception.", e);
            return new ApiResponse<>(ApiResponse.Status.fail());
        }
    }
}
