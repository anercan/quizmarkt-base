package com.quizmarkt.base.controller;

import com.quizmarkt.base.data.request.GoogleLoginRequest;
import com.quizmarkt.base.data.request.SignInRequest;
import com.quizmarkt.base.data.response.SignInResponse;
import com.quizmarkt.base.service.UserManagementService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author anercan
 */

@RestController
@AllArgsConstructor
@RequestMapping("/user-management")
public class UserManagementController {

    private final UserManagementService userManagementService;

    @PostMapping("/sign-in-basic")
    public ResponseEntity<SignInResponse> signInWithMail(@RequestBody SignInRequest signInRequest) {
        return userManagementService.signInWithMail(signInRequest);
    }

    @PostMapping("/google-sign-in")
    public ResponseEntity<SignInResponse> signInWithMail(@RequestBody GoogleLoginRequest request) {
        return userManagementService.signInWithGoogle(request);
    }

    @PostMapping("/update-premium-info")
    public ResponseEntity<String> updatePremiumInfo() {
        return userManagementService.updatePremiumInfo();
    }

}
