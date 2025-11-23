package com.quizmarkt.base.controller;

import com.quizmarkt.base.data.request.SaveUserDailyQuizRequest;
import com.quizmarkt.base.data.response.ApiResponse;
import com.quizmarkt.base.data.response.UserDailyQuizWithInfoResponse;
import com.quizmarkt.base.service.UserDailyQuizService;
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
@RequestMapping("/quiz")
public class DailyQuizController extends BaseController {

    private final UserDailyQuizService userDailyQuizService;

    @PostMapping("/get-user-daily-quiz")
    public ResponseEntity<ApiResponse<UserDailyQuizWithInfoResponse>> getUserDailyQuiz() {
        return respond(userDailyQuizService.getUserDailyQuiz());
    }

    @PostMapping("/save-user-daily-quiz")
    public ResponseEntity<ApiResponse<Boolean>> saveUserDailyQuiz(@RequestBody SaveUserDailyQuizRequest request) {
        return respond(userDailyQuizService.saveUserDailyQuiz(request));
    }
}
