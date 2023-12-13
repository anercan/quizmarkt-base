package com.quesmarkt.quesmarktbase.controller;

import com.quesmarkt.quesmarktbase.data.request.QuizListWithUserDataRequest;
import com.quesmarkt.quesmarktbase.data.response.QuizListResponse;
import com.quesmarkt.quesmarktbase.data.response.QuizResponse;
import com.quesmarkt.quesmarktbase.service.QuizService;
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
    public ResponseEntity<QuizResponse> getQuizWithUserQuizDataForStartTest(@RequestParam Long quizId) {
        return quizService.getQuizWithUserQuizDataForStartTest(quizId);
    }
}
