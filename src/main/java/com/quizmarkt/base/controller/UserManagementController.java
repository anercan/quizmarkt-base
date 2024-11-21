package com.quizmarkt.base.controller;

import com.quizmarkt.base.data.request.GoogleLoginRequest;
import com.quizmarkt.base.data.request.GoogleSubscriptionRequest;
import com.quizmarkt.base.data.response.JwtResponse;
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

    @PostMapping("/google-sign-in")
    public ResponseEntity<JwtResponse> googleOATH2(@RequestBody GoogleLoginRequest request) {
        return userManagementService.signInWithGoogle(request);
    }

    @PostMapping("/google-play-subscribe")
    public ResponseEntity<JwtResponse> googlePlaySubscribe(@RequestBody GoogleSubscriptionRequest googleSubscriptionRequest) {
        return userManagementService.googlePlaySubscribe(googleSubscriptionRequest);
    }

}
