package com.quizmarkt.base.controller;

import com.quizmarkt.base.data.entity.Question;
import com.quizmarkt.base.data.entity.Quiz;
import com.quizmarkt.base.data.entity.QuizGroup;
import com.quizmarkt.base.data.request.PageRequest;
import com.quizmarkt.base.data.request.QuizListWithGroupIdRequest;
import com.quizmarkt.base.data.request.admin.CreateOrUpdateQuestion;
import com.quizmarkt.base.data.request.admin.CreateOrUpdateQuiz;
import com.quizmarkt.base.data.request.admin.CreateOrUpdateQuizGroup;
import com.quizmarkt.base.data.request.admin.GenerateMixedQuiz;
import com.quizmarkt.base.service.AdminCRUDService;
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

    private final AdminCRUDService adminService;

    @PostMapping("/get-quiz-groups")
    public ResponseEntity<List<QuizGroup>> getQuizGroupList(@RequestBody PageRequest request) {
        return adminService.getQuizGroupList(request);
    }

    @PostMapping("/get-quizzes")
    public ResponseEntity<List<Quiz>> getQuizList(@RequestBody QuizListWithGroupIdRequest request) {
        return adminService.getQuizListWithGroupIfExist(request);
    }

    @GetMapping("/get-question-list")
    public ResponseEntity<List<Question>> getQuizGroupList(@RequestParam Long quizId) {
        return adminService.getQuestionList(quizId);
    }

    @PostMapping("/save-quiz-group")
    public ResponseEntity<Void> getQuizGroupList(@RequestBody CreateOrUpdateQuizGroup request) {
        return adminService.saveQuizGroup(request);
    }

    @PostMapping("/save-quiz")
    public ResponseEntity<Void> getQuizGroupList(@RequestBody CreateOrUpdateQuiz request) {
        return adminService.saveQuiz(request);
    }

    @PostMapping("/save-question")
    public ResponseEntity<Void> getQuizGroupList(@RequestBody CreateOrUpdateQuestion request) {
        return adminService.saveQuestion(request);
    }

    @PostMapping("/generate-mixed")
    public ResponseEntity<Void> generateMixed(@RequestBody GenerateMixedQuiz request) {
        return null;
    }

}
