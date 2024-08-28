package com.quesmarkt.quesmarktbase.controller;

import com.quesmarkt.quesmarktbase.data.entity.Quiz;
import com.quesmarkt.quesmarktbase.data.entity.QuizGroup;
import com.quesmarkt.quesmarktbase.data.request.CreateOrUpdateQuizGroup;
import com.quesmarkt.quesmarktbase.data.request.PageRequest;
import com.quesmarkt.quesmarktbase.service.QuizGroupService;
import com.quesmarkt.quesmarktbase.service.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author anercan
 */

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final QuizService quizService;
    private final QuizGroupService quizGroupService;

    @PostMapping("/get-quiz-groups")
    public ResponseEntity<List<QuizGroup>> getQuizGroupList(@RequestBody PageRequest request) {
        return quizGroupService.getQuizGroupList(request);
    }


    @PostMapping("/save-quiz-group")
    public ResponseEntity<Void> getQuizGroupList(@RequestBody CreateOrUpdateQuizGroup request) {
        return quizGroupService.saveQuizGroup(request);
    }

    @PostMapping("/get-quizes")
    public ResponseEntity<List<Quiz>> getQuizList(@RequestBody PageRequest pageRequest) {
        return quizService.getQuizList(pageRequest);
    }

}
