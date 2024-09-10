package com.quizmarkt.base.controller;

import com.quizmarkt.base.data.entity.Question;
import com.quizmarkt.base.data.entity.Quiz;
import com.quizmarkt.base.data.entity.QuizGroup;
import com.quizmarkt.base.data.request.*;
import com.quizmarkt.base.service.QuestionService;
import com.quizmarkt.base.service.QuizGroupService;
import com.quizmarkt.base.service.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author anercan
 */

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    //todo role based

    private final QuizService quizService;
    private final QuizGroupService quizGroupService;
    private final QuestionService questionService;

    @PostMapping("/get-quiz-groups")
    public ResponseEntity<List<QuizGroup>> getQuizGroupList(@RequestBody PageRequest request) {
        return quizGroupService.getQuizGroupList(request);
    }

    @PostMapping("/save-quiz-group")
    public ResponseEntity<Void> getQuizGroupList(@RequestBody CreateOrUpdateQuizGroup request) {
        return quizGroupService.saveQuizGroup(request);
    }

    @PostMapping("/get-quizzes")
    public ResponseEntity<List<Quiz>> getQuizList(@RequestBody QuizListWithGroupIdRequest request) {
        return quizService.getQuizListWithGroupIfExist(request);
    }

    @PostMapping("/save-quiz")
    public ResponseEntity<Void> getQuizGroupList(@RequestBody CreateOrUpdateQuiz request) {
        return quizService.saveQuiz(request);
    }

    @PostMapping("/save-question")
    public ResponseEntity<Void> getQuizGroupList(@RequestBody CreateOrUpdateQuestion request) {
        return questionService.saveQuestion(request);
    }

    @GetMapping("/get-question-list")
    public ResponseEntity<List<Question>> getQuizGroupList(@RequestParam Long quizId) {
        return questionService.getQuestionList(quizId);
    }

}
