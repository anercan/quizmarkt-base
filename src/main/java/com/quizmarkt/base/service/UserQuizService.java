package com.quizmarkt.base.service;

import com.quizmarkt.base.data.entity.QuizGroup;
import com.quizmarkt.base.data.entity.UserQuiz;
import com.quizmarkt.base.data.enums.UserQuizState;
import com.quizmarkt.base.data.mapper.UserQuizMapper;
import com.quizmarkt.base.data.request.CreateUpdateUserQuizRequest;
import com.quizmarkt.base.data.request.ReviewWrongQuestionsRequest;
import com.quizmarkt.base.data.response.*;
import com.quizmarkt.base.manager.UserQuizManager;
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
public class UserQuizService extends BaseService {

    private final UserQuizManager userQuizManager;
    private final UserQuizMapper userQuizMapper;

    public ResponseEntity<UserQuizListResponse> getUserQuizList() {
        List<UserQuizInListResponse> userQuizInListResponses = new ArrayList<>();
        List<UserQuiz> userQuizList = userQuizManager.getOrderedUserQuizList();
        for (UserQuiz userQuiz : userQuizList) {
            UserQuizInListResponse userQuizInListResponse = userQuizMapper.toUserQuizListResponse(userQuiz);
            Long quizGroupId = userQuizInListResponse.getQuizGroupId();
            userQuizInListResponse.setQuizGroupName(getQuizGroupNameFromUserQuiz(userQuiz, quizGroupId));
            userQuizInListResponses.add(userQuizInListResponse);
        }
        return ResponseEntity.ok(UserQuizListResponse.builder()
                .userQuizResponseList(userQuizInListResponses)
                .build());
    }

    private String getQuizGroupNameFromUserQuiz(UserQuiz userQuiz, Long quizGroupId) {
        return userQuiz.getQuiz().getQuizGroupList()
                .stream()
                .filter(quizGroup -> quizGroup.getId().equals(quizGroupId))
                .findFirst().map(QuizGroup::getTitle)
                .orElse(null);
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

    public ResponseEntity<CompletedStaticsResponse> getCompletedQuizStatics(Long quizId) {
        try {
            int better = 0;
            int worse = 0;
            int equal = 0;
            Optional<UserQuiz> completedUserQuizOpt = userQuizManager.getUserQuizWithQuizIdAndUserId(quizId);
            if (completedUserQuizOpt.isPresent()) {
                List<UserQuiz> otherUserQuizzes = userQuizManager.getUserQuizWithQuizId(quizId);
                for (UserQuiz otherUserQuiz: otherUserQuizzes) {
                    if (otherUserQuiz.getWrongQuestionList().size() > completedUserQuizOpt.get().getWrongQuestionList().size()) {
                        better++;
                    } else if (otherUserQuiz.getWrongQuestionList().size() == completedUserQuizOpt.get().getWrongQuestionList().size()) {
                        equal++;
                    } else if (otherUserQuiz.getWrongQuestionList().size() < completedUserQuizOpt.get().getWrongQuestionList().size()) {
                        worse++;
                    }
                }
                return ResponseEntity.ok(CompletedStaticsResponse.builder().betterCount(better).equalCount(equal).worseCount(worse).build());
            }
        } catch (Exception e) {
            logger.error("getCompletedQuizStatics got exception",e);
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.internalServerError().build();
    }
}
