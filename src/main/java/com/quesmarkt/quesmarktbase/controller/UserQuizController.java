package com.quesmarkt.quesmarktbase.controller;

import com.quesmarkt.quesmarktbase.data.request.CreateUpdateUserQuizRequest;
import com.quesmarkt.quesmarktbase.data.response.UserQuizResponse;
import com.quesmarkt.quesmarktbase.service.UserQuizService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author anercan
 */

@RestController
@AllArgsConstructor
@RequestMapping("/user-quiz")
public class UserQuizController {

    private final UserQuizService userQuizService;

    @GetMapping("/get-user-quiz-list") //todo pagenation
    public ResponseEntity<List<UserQuizResponse>> getUserQuizList() {
        return userQuizService.getUserQuizList();
    }

    @PostMapping("/create-update-user-quiz")
    public ResponseEntity<List<UserQuizResponse>> createUpdateUserQuiz(CreateUpdateUserQuizRequest request) {
        return null;
    }

}
