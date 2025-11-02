package com.quizmarkt.base.controller;

import com.quizmarkt.base.data.entity.Question;
import com.quizmarkt.base.data.entity.Quiz;
import com.quizmarkt.base.data.entity.QuizGroup;
import com.quizmarkt.base.data.request.PageRequest;
import com.quizmarkt.base.data.request.QuizListWithGroupIdRequest;
import com.quizmarkt.base.data.request.SignInRequest;
import com.quizmarkt.base.data.request.UserFilterRequest;
import com.quizmarkt.base.data.request.admin.CreateOrUpdateQuestion;
import com.quizmarkt.base.data.request.admin.CreateOrUpdateQuiz;
import com.quizmarkt.base.data.request.admin.CreateOrUpdateQuizGroup;
import com.quizmarkt.base.data.request.admin.FillQuizRequest;
import com.quizmarkt.base.data.response.JwtResponse;
import com.quizmarkt.base.data.response.UserResponse;
import com.quizmarkt.base.service.AdminCRUDService;
import com.quizmarkt.base.util.ConfigUtils;
import com.quizmarkt.base.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
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
    private final Environment environment;

    @ModelAttribute
    public void before() {
        boolean notContainsLogin = !httpServletRequest.getRequestURI().contains("login");
        if (notContainsLogin && !ConfigUtils.isLocalProfileActive(environment)) {
            Claims claims = JwtUtil.checkAndGetJWTClaims(JwtUtil.getJwtFromRequest(httpServletRequest));
            if (!(claims != null && claims.get("ROLE").toString().equals("ADMIN"))) {
                throw new RuntimeException("Invalid admin request");
            }
        }
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> adminLogin(@RequestBody SignInRequest request) {
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
    public ResponseEntity<Void> saveQuestion(@RequestBody CreateOrUpdateQuestion request) {
        return adminService.saveQuestion(request);
    }

    @PostMapping("/save-question-all")
    public ResponseEntity<Void> saveQuestionAll(@RequestBody List<CreateOrUpdateQuestion> request) {
        request.forEach(adminService::saveQuestion);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/get-users-filter")
    public ResponseEntity<List<UserResponse>> getFilteredUsers(@RequestBody UserFilterRequest request) {
        return adminService.getFilteredUsers(request);
    }

    @PostMapping("/fill-with-questions")
    public ResponseEntity<Boolean> fillQuizWithQuestion(@RequestBody FillQuizRequest request) {
        return adminService.fillQuizWithQuestions(request);
    }
}
