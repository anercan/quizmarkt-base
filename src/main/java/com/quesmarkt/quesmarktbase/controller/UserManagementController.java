package com.quesmarkt.quesmarktbase.controller;

import com.quesmarkt.quesmarktbase.data.request.SignInRequest;
import com.quesmarkt.quesmarktbase.data.request.SignUpRequest;
import com.quesmarkt.quesmarktbase.data.response.SignInResponse;
import com.quesmarkt.quesmarktbase.data.response.SignUpResponse;
import com.quesmarkt.quesmarktbase.service.UserManagementService;
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

    public ResponseEntity<SignUpResponse> signUpWithMail(@RequestBody SignUpRequest signUpRequest) {
        return null;
    }
}
