package com.quesmarkt.quesmarktbase.service;

import com.quesmarkt.quesmarktbase.data.entity.UserQuiz;
import com.quesmarkt.quesmarktbase.data.enums.UserQuizState;
import com.quesmarkt.quesmarktbase.data.mapper.UserQuizMapper;
import com.quesmarkt.quesmarktbase.data.request.CreateUpdateUserQuizRequest;
import com.quesmarkt.quesmarktbase.data.request.ReviewWrongQuestionsRequest;
import com.quesmarkt.quesmarktbase.data.response.BooleanResponse;
import com.quesmarkt.quesmarktbase.data.response.ReviewWrongQuestionListResponse;
import com.quesmarkt.quesmarktbase.data.response.UserQuizListResponse;
import com.quesmarkt.quesmarktbase.manager.UserQuizManager;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author anercan
 */

@Service
@AllArgsConstructor
public class UserQuizService {

    private final UserQuizManager userQuizManager;
    private final UserQuizMapper userQuizMapper;

    public ResponseEntity<UserQuizListResponse> getUserQuizList() {
        try {
            List<UserQuiz> userQuizList = userQuizManager.getUserQuizList();
            return ResponseEntity.ok(UserQuizListResponse.builder()
                            .userQuizResponseList(userQuizMapper.toUserQuizListResponse(userQuizList))
                            .build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<BooleanResponse> createUpdateUserQuiz(CreateUpdateUserQuizRequest request) {
        Optional<UserQuiz> optionalUserQuiz = userQuizManager.getUserQuizWithQuizIdAndUserId(request.getQuizId());
        if (optionalUserQuiz.isEmpty()) {
            UserQuiz userQuiz = userQuizManager.createNewUserQuiz(request);
            return ResponseEntity.ok(BooleanResponse.builder().value(Objects.nonNull(userQuiz)).build());
        } else {
            UserQuiz userQuiz = userQuizManager.updateUserQuiz(request, optionalUserQuiz.get());
            return ResponseEntity.ok(BooleanResponse.builder().value(Objects.nonNull(userQuiz)).build());
        }
    }

    public ResponseEntity<ReviewWrongQuestionListResponse> reviewWrongsForUserQuiz(ReviewWrongQuestionsRequest request) {
        Optional<UserQuiz> userQuizOptional = userQuizManager.getUserQuizWithQuizIdAndUserId(request.getQuizId());
        if (userQuizOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        UserQuiz userQuiz = userQuizOptional.get();
        if (!UserQuizState.COMPLETED.equals(userQuiz.getState())) {
            return ResponseEntity.badRequest().build();
        }
        if (CollectionUtils.isEmpty(userQuiz.getWrongQuestionList())) {
            return ResponseEntity.ok(ReviewWrongQuestionListResponse.builder().reviewWrongQuestionList(new ArrayList<>()).build());
        }
        return ResponseEntity.ok(
                ReviewWrongQuestionListResponse.builder()
                        .reviewWrongQuestionList(userQuizMapper.toListUserWrongAnswerResponse(userQuiz.getWrongQuestionList()))
                        .build()
        );
    }
}
