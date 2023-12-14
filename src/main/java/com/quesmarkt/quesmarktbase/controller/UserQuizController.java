package com.quesmarkt.quesmarktbase.controller;

import com.quesmarkt.quesmarktbase.data.request.CreateUpdateUserQuizRequest;
import com.quesmarkt.quesmarktbase.data.request.ReviewWrongQuestionsRequest;
import com.quesmarkt.quesmarktbase.data.response.BooleanResponse;
import com.quesmarkt.quesmarktbase.data.response.ReviewWrongQuestionListResponse;
import com.quesmarkt.quesmarktbase.data.response.UserQuizListResponse;
import com.quesmarkt.quesmarktbase.service.UserQuizService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author anercan
 */

@RestController
@AllArgsConstructor
@RequestMapping("/user-quiz")
public class UserQuizController {

    private final UserQuizService userQuizService;

    @GetMapping("/get-user-quiz-list") //todo pagenation
    public ResponseEntity<UserQuizListResponse> getUserQuizList() {
        return userQuizService.getUserQuizList();
    }

    @PostMapping("/create-update-user-quiz")
    public ResponseEntity<BooleanResponse> createUpdateUserQuiz(@RequestBody CreateUpdateUserQuizRequest request) {
        return userQuizService.createUpdateUserQuiz(request);
    }

    @PostMapping("/review-wrong-questions")
    public ResponseEntity<ReviewWrongQuestionListResponse> reviewWrongQuestionList(@RequestBody ReviewWrongQuestionsRequest request) {
        return userQuizService.reviewWrongsForUserQuiz(request);
    }
}
