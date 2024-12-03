package com.quizmarkt.base.service;

import com.quizmarkt.base.data.entity.UserQuiz;
import com.quizmarkt.base.data.entity.UserWrongAnswer;
import com.quizmarkt.base.data.enums.UserQuizState;
import com.quizmarkt.base.data.response.ActivityData;
import com.quizmarkt.base.data.response.UserDataResponse;
import com.quizmarkt.base.data.response.UserInfo;
import com.quizmarkt.base.manager.QuizManager;
import com.quizmarkt.base.manager.UserManagementManager;
import com.quizmarkt.base.manager.UserQuizManager;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author anercan
 */

@Service
@AllArgsConstructor
public class ProfileService extends BaseService {

    private final UserQuizManager userQuizManager;
    private final QuizManager quizManager;
    private final UserManagementManager userManagementManager;

    public ResponseEntity<UserDataResponse> getUserData() {
        Optional<UserInfo> userInfo = getUserInfo();

        if (isRegularPremium()) {
            List<UserQuiz> userQuizList = userQuizManager.getUserQuizList();
            long completedQuizCount = userQuizList.stream()
                    .filter(userQuiz -> UserQuizState.COMPLETED.equals(userQuiz.getState()))
                    .count();
            long ongoingQuizCount = userQuizList.stream()
                    .filter(userQuiz -> UserQuizState.ON_GOING.equals(userQuiz.getState()))
                    .count();

            UserDataResponse build = UserDataResponse.builder()
                    .totalQuizCount(quizManager.getActiveQuizCount())
                    .userSolvedQuizCount(completedQuizCount)
                    .userOngoingQuizCount(ongoingQuizCount)
                    .wrongsMap(getWrongsInfo(userQuizList))
                    .avatarUrl(userInfo.map(UserInfo::getAvatarUrl).orElse(null))
                    .activityDataList(getActivityData(userQuizList))
                    .build();
            return ResponseEntity.ok(build);
        } else {
            UserDataResponse build = UserDataResponse.builder()
                    .totalQuizCount(quizManager.getActiveQuizCount())
                    .userSolvedQuizCount(userQuizManager.getUserQuizCount(UserQuizState.COMPLETED))
                    .userOngoingQuizCount(userQuizManager.getUserQuizCount(UserQuizState.ON_GOING))
                    .avatarUrl(userInfo.map(UserInfo::getAvatarUrl).orElse(null))
                    .build();
            return ResponseEntity.ok(build);
        }
    }

    private List<ActivityData> getActivityData(List<UserQuiz> userQuizList) {
        try {
            Map<LocalDate, Long> groupedByDate = userQuizList.stream()
                    .collect(Collectors.groupingBy(
                            userQuiz -> userQuiz.getCompleteDate().toLocalDate(),
                            Collectors.counting()
                    ));

            return groupedByDate.entrySet().stream()
                    .map(entry -> new ActivityData(entry.getKey().toString(), entry.getValue().intValue()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("getActivityData got exception userId:{}",getUserId());
            return Collections.emptyList();
        }
    }

    private Optional<UserInfo> getUserInfo() {
        UserInfo userInfo = userManagementManager.getUserInfo(getUserId());
        if (userInfo != null) {
            return Optional.of(userInfo);
        }
        return Optional.empty();
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
