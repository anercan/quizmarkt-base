package com.quizmarkt.base.controller;

import com.quizmarkt.base.data.response.UserDataResponse;
import com.quizmarkt.base.service.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author anercan
 */

@RestController
@AllArgsConstructor
@RequestMapping("/profile")
public class UserProfileController {

    private final ProfileService profileService;

    @GetMapping("/get-user-info")
    public ResponseEntity<UserDataResponse> getUserData() {
        return profileService.getUserData();
    }
}
