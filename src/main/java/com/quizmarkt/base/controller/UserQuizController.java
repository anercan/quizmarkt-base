package com.quizmarkt.base.controller;

import com.quizmarkt.base.data.request.CreateUpdateUserQuizRequest;
import com.quizmarkt.base.data.response.ApiResponse;
import com.quizmarkt.base.data.response.BooleanResponse;
import com.quizmarkt.base.data.response.CompletedStaticsResponse;
import com.quizmarkt.base.data.response.UserQuizListResponse;
import com.quizmarkt.base.service.UserQuizService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author anercan
 */

@RestController
@AllArgsConstructor
@RequestMapping("/user-quiz")
public class UserQuizController extends BaseController {

    private final UserQuizService userQuizService;

    @GetMapping("/get-user-quiz-list") //todo pagenation
    public ResponseEntity<ApiResponse<UserQuizListResponse>> getUserQuizList() {
        return respond(userQuizService.getUserQuizList());
    }

    @PostMapping("/create-update-user-quiz")
    public ResponseEntity<ApiResponse<BooleanResponse>> createUpdateUserQuiz(@RequestBody CreateUpdateUserQuizRequest request) {
        return respond(userQuizService.createUpdateUserQuiz(request));
    }

    @GetMapping("/get-completed-quiz-statics")
    public ResponseEntity<ApiResponse<CompletedStaticsResponse>> getCompletedQuizStatics(@RequestParam Long quizId) {
        return respond(userQuizService.getCompletedQuizStatics(quizId));
    }

}
