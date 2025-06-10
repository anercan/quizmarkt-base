package com.quizmarkt.base.controller;

import com.quizmarkt.base.data.request.QuizListWithUserDataRequest;
import com.quizmarkt.base.data.request.SaveUserDailyQuizRequest;
import com.quizmarkt.base.data.response.ApiResponse;
import com.quizmarkt.base.data.response.QuizListResponse;
import com.quizmarkt.base.data.response.QuizResponse;
import com.quizmarkt.base.data.response.UserDailyQuizWithInfoResponse;
import com.quizmarkt.base.service.QuizService;
import com.quizmarkt.base.service.UserDailyQuizService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author anercan
 */

@RestController
@AllArgsConstructor
@RequestMapping("/quiz")
public class QuizController extends BaseController {

    private final QuizService quizService;
    private final UserDailyQuizService userDailyQuizService;

    @PostMapping("/get-quizzes-with-user-data")
    public ResponseEntity<ApiResponse<QuizListResponse>> getQuizListWithUserData(@RequestBody QuizListWithUserDataRequest request) {
        return respond(quizService.getQuizListWithUserData(request));
    }

    @GetMapping("/get-quiz-with-id/{quizId}")
    public ResponseEntity<ApiResponse<QuizResponse>> getQuizWithUserQuizData(@PathVariable String quizId) {
        return respond(quizService.getQuizWithUserQuizData(Long.parseLong(quizId)));
    }

    @PostMapping("/get-user-daily-quiz")
    public ResponseEntity<ApiResponse<UserDailyQuizWithInfoResponse>> getUserDailyQuiz() {
        return respond(userDailyQuizService.getUserDailyQuiz());
    }

    @PostMapping("/save-user-daily-quiz")
    public ResponseEntity<ApiResponse<Boolean>> saveUserDailyQuiz(@RequestBody SaveUserDailyQuizRequest request) {
        return respond(userDailyQuizService.saveUserDailyQuiz(request));
    }
}
