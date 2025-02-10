package com.quizmarkt.base.controller;

import com.quizmarkt.base.data.entity.Question;
import com.quizmarkt.base.data.entity.Quiz;
import com.quizmarkt.base.data.entity.QuizGroup;
import com.quizmarkt.base.data.request.PageRequest;
import com.quizmarkt.base.data.request.QuizListWithGroupIdRequest;
import com.quizmarkt.base.data.request.SignInRequest;
import com.quizmarkt.base.data.request.admin.CreateOrUpdateQuestion;
import com.quizmarkt.base.data.request.admin.CreateOrUpdateQuiz;
import com.quizmarkt.base.data.request.admin.CreateOrUpdateQuizGroup;
import com.quizmarkt.base.data.response.JwtResponse;
import com.quizmarkt.base.service.AdminCRUDService;
import com.quizmarkt.base.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
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

    private final AdminCRUDService adminService;
    private final HttpServletRequest httpServletRequest;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> getQuizGroupList(@RequestBody SignInRequest request) {
        return adminService.adminLogin(request);
    }

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

    @ModelAttribute
    public void before() {
        Claims claims = JwtUtil.getClaims(JwtUtil.getJwtFromRequest(httpServletRequest));
        if (!(claims != null && claims.get("ROLE").toString().equals("ADMIN"))) {
            throw new RuntimeException("Invalid admin request");
        }
    }
}
