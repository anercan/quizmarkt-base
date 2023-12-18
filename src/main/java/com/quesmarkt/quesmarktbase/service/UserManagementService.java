package com.quesmarkt.quesmarktbase.service;

import com.quesmarkt.quesmarktbase.data.request.SignInRequest;
import com.quesmarkt.quesmarktbase.data.response.SignInResponse;
import com.quesmarkt.quesmarktbase.manager.UserManagementManager;
import com.quesmarkt.quesmarktbase.manager.exception.CallWebServiceException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author anercan
 */

@Service
@AllArgsConstructor
public class UserManagementService {

    private final UserManagementManager userManagementManager;

    public ResponseEntity<SignInResponse> signInWithMail(SignInRequest signInRequest) {
        if (StringUtils.isEmpty(signInRequest.getMail()) || StringUtils.isEmpty(signInRequest.getPassword())) {
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
}
