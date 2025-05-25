package com.quizmarkt.base.controller;

import com.quizmarkt.base.data.response.ApiResponse;
import com.quizmarkt.base.data.response.UserActivityData;
import com.quizmarkt.base.data.response.UserDataResponse;
import com.quizmarkt.base.data.response.UserQuizAnalyseResponse;
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
public class UserProfileController extends BaseController {

    private final ProfileService profileService;

    @GetMapping("/get-user-info")
    public ResponseEntity<ApiResponse<UserDataResponse>> getUserData() {
        return respond(profileService.getUserData());
    }

    @GetMapping("/get-user-quiz-analyses")
    public ResponseEntity<ApiResponse<UserQuizAnalyseResponse>> getUserQuizAnalyses() {
        return respond(profileService.getUserQuizAnalyses());
    }

    @GetMapping("/get-user-activity-data")
    public ResponseEntity<ApiResponse<UserActivityData>> getUserActivityData() {
        return respond(profileService.getUserActivityData());
    }
}
