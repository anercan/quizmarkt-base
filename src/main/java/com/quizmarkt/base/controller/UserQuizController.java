package com.quizmarkt.base.controller;

import com.quizmarkt.base.data.request.CreateUpdateUserQuizRequest;
import com.quizmarkt.base.data.request.ReviewWrongQuestionsRequest;
import com.quizmarkt.base.data.response.BooleanResponse;
import com.quizmarkt.base.data.response.CompletedStaticsResponse;
import com.quizmarkt.base.data.response.ReviewWrongQuestionListResponse;
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

    @GetMapping("/get-completed-quiz-statics")
    public ResponseEntity<CompletedStaticsResponse> getCompletedQuizStatics(@RequestParam Long quizId) {
        return userQuizService.getCompletedQuizStatics(quizId);
    }

}
