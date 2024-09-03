package com.quizmarkt.base.controller;

import com.quizmarkt.base.data.request.QuizListWithUserDataRequest;
import com.quizmarkt.base.data.response.QuizListResponse;
import com.quizmarkt.base.data.response.QuizResponse;
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
public class QuizController {

    private final QuizService quizService;

    @PostMapping("/get-quizzes-with-user-data")
    public ResponseEntity<QuizListResponse> getQuizListWithUserData(@RequestBody QuizListWithUserDataRequest request) {
        return quizService.getQuizListWithUserData(request);
    }

    @GetMapping("/get-quiz-with-id/{quizId}")
    public ResponseEntity<QuizResponse> getQuizWithUserQuizDataForStartTest(@PathVariable String quizId) {
        return quizService.getQuizWithUserQuizDataForStartTest(Long.parseLong(quizId));
    }
}
