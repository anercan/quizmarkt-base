package com.quizmarkt.base.controller;

import com.quizmarkt.base.data.request.QuizListWithUserDataRequest;
import com.quizmarkt.base.data.response.ApiResponse;
import com.quizmarkt.base.data.response.QuizListResponse;
import com.quizmarkt.base.data.response.QuizResponseWithUserQuizData;
import com.quizmarkt.base.service.QuizService;
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

    @PostMapping("/get-quizzes-with-user-data")
    public ResponseEntity<ApiResponse<QuizListResponse>> getQuizListWithUserData(@RequestBody QuizListWithUserDataRequest request) {
        return respond(quizService.getQuizListWithUserData(request));
    }

    @GetMapping("/get-quiz-with-id/{quizId}")
    public ResponseEntity<ApiResponse<QuizResponseWithUserQuizData>> getQuizWithUserQuizData(@PathVariable String quizId) {
        return respond(quizService.getQuizWithUserQuizData(Long.parseLong(quizId)));
    }
}
