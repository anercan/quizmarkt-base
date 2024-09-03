package com.quizmarkt.base.controller;

import com.quizmarkt.base.data.request.QuizGroupRequest;
import com.quizmarkt.base.data.response.QuizGroupResponse;
import com.quizmarkt.base.service.QuizGroupService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author anercan
 */

@RestController
@AllArgsConstructor
@RequestMapping("/quiz-group")
public class QuizGroupController {

    private final QuizGroupService quizGroupService;

    @PostMapping("/get-quiz-groups-with-user-quiz-data")
    public ResponseEntity<QuizGroupResponse> getQuizGroupsWithUserData(@RequestBody QuizGroupRequest request) {
        return quizGroupService.getQuizGroupsWithUserInfo(request);
    }
}
