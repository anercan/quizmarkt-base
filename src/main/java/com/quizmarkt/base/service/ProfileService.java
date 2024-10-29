package com.quizmarkt.base.service;

import com.quizmarkt.base.data.entity.UserQuiz;
import com.quizmarkt.base.data.entity.UserWrongAnswer;
import com.quizmarkt.base.data.enums.UserQuizState;
import com.quizmarkt.base.data.response.UserDataResponse;
import com.quizmarkt.base.manager.QuizManager;
import com.quizmarkt.base.manager.UserQuizManager;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author anercan
 */

@Service
@AllArgsConstructor
public class ProfileService extends BaseService {

    private final UserQuizManager userQuizManager;
    private final QuizManager quizManager;

    public ResponseEntity<UserDataResponse> getUserData() {
        if (isRegularPremium()) {
            List<UserQuiz> userQuizList = userQuizManager.getUserQuizList();
            long completedQuizCount = userQuizList.stream()
                    .filter(userQuiz -> UserQuizState.COMPLETED.equals(userQuiz.getState()))
                    .count();
            long ongoingQuizCount = userQuizList.stream()
                    .filter(userQuiz -> UserQuizState.ON_GOING.equals(userQuiz.getState()))
                    .count();

            Map<String, Integer> wrongsInfo = getWrongsInfo(userQuizList);
            UserDataResponse build = UserDataResponse.builder()
                    .totalQuizCount(quizManager.getActiveQuizCount())
                    .userSolvedQuizCount(completedQuizCount)
                    .userOngoingQuizCount(ongoingQuizCount)
                    .wrongsMap(wrongsInfo)
                    .avatarUrl("https://lh3.googleusercontent.com/a/ACg8ocKd5hcKqsKsk1ol2I6auAMVewmw0AJzy16BMZQti4UIvE_a1g=s96-c") //todo
                    .build();
            return ResponseEntity.ok(build);
        } else {
            UserDataResponse build = UserDataResponse.builder()
                    .totalQuizCount(quizManager.getActiveQuizCount())
                    .userSolvedQuizCount(userQuizManager.getUserQuizCount(UserQuizState.COMPLETED))
                    .userOngoingQuizCount(userQuizManager.getUserQuizCount(UserQuizState.ON_GOING))
                    .avatarUrl("https://lh3.googleusercontent.com/a/ACg8ocKd5hcKqsKsk1ol2I6auAMVewmw0AJzy16BMZQti4UIvE_a1g=s96-c") //todo
                    .build();
            return ResponseEntity.ok(build);
        }
    }

    private Map<String, Integer> getWrongsInfo(List<UserQuiz> userQuizList) {
        try {
            return userQuizList.stream()
                    .flatMap(userQuiz ->
                            userQuiz.getWrongQuestionList()
                                    .stream()
                                    .map(UserWrongAnswer::getQuestion)
                    ).filter(question -> StringUtils.isNotEmpty(question.getAttributes().get("subject")))
                    .collect(Collectors.toMap(
                            question -> question.getAttributes().get("subject"),
                            question -> 1,
                            Integer::sum
                    ));
        } catch (Exception e) {
            logger.error("getWrongsInfo got exception.userID:{}",getUserId(),e);
            return Collections.emptyMap();
        }
    }

}
