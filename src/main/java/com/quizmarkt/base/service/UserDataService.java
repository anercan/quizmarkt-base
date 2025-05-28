package com.quizmarkt.base.service;

import com.quizmarkt.base.data.constant.CacheConstants;
import com.quizmarkt.base.data.entity.UserQuiz;
import com.quizmarkt.base.data.entity.UserWrongAnswer;
import com.quizmarkt.base.data.enums.UserQuizState;
import com.quizmarkt.base.data.response.ActivityData;
import com.quizmarkt.base.data.response.UserDataResponse;
import com.quizmarkt.base.data.response.UserInfo;
import com.quizmarkt.base.manager.QuizManager;
import com.quizmarkt.base.manager.UserQuizManager;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author anercan
 */

@Service
@AllArgsConstructor
public class UserDataService extends BaseService {

    private final UserQuizManager userQuizManager;
    private final QuizManager quizManager;

    @Cacheable(value = CacheConstants.USER_DATA, key = "#userId+#appId+#regularPremium")  //todo sadece userid yeterli olacak
    public UserDataResponse getPremiumUserData(Optional<UserInfo> userInfo, String userId, Integer appId, boolean regularPremium) {
        List<UserQuiz> userQuizList = userQuizManager.getUserQuizList(userId);
        long completedQuizCount = userQuizList.stream()
                .filter(userQuiz -> UserQuizState.COMPLETED.equals(userQuiz.getState()))
                .count();
        long ongoingQuizCount = userQuizList.stream()
                .filter(userQuiz -> UserQuizState.ON_GOING.equals(userQuiz.getState()))
                .count();

        return UserDataResponse.builder()
                .totalQuizCount(quizManager.getActiveQuizCount())
                .userSolvedQuizCount(completedQuizCount)
                .userOngoingQuizCount(ongoingQuizCount)
                .wrongsMap(getWrongsInfo(userQuizList))
                .avatarUrl(userInfo.map(UserInfo::getAvatarUrl).orElse(null))
                .activityDataList(getActivityData(userQuizList))
                .build();
    }

    @Cacheable(value = CacheConstants.USER_DATA, key = "#userId+#appId+#regularPremium") //todo sadece userid yeterli olacak
    public UserDataResponse getNonPremiumUserData(Optional<UserInfo> userInfo, String userId, Integer appId, boolean regularPremium) {
        return UserDataResponse.builder()
                .totalQuizCount(quizManager.getActiveQuizCount())
                .userSolvedQuizCount(userQuizManager.getUserQuizCount(UserQuizState.COMPLETED))
                .userOngoingQuizCount(userQuizManager.getUserQuizCount(UserQuizState.ON_GOING))
                .avatarUrl(userInfo.map(UserInfo::getAvatarUrl).orElse(null))
                .build();
    }

    public List<ActivityData> getActivityData(List<UserQuiz> userQuizList) {
        if (CollectionUtils.isEmpty(userQuizList)) {
            return Collections.emptyList();
        }
        try {
            Map<LocalDate, Long> groupedByDate = userQuizList.stream().filter(userQuiz -> Objects.nonNull(userQuiz.getStartDate()))
                    .collect(Collectors.groupingBy(
                            userQuiz -> userQuiz.getStartDate().toLocalDate(),
                            Collectors.counting()
                    ));

            return groupedByDate.entrySet().stream()
                    .map(entry -> new ActivityData(entry.getKey().toString(), entry.getValue().intValue()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("getActivityData got exception userId:{}", getUserId());
            return Collections.emptyList();
        }
    }

    public Map<String, Integer> getWrongsInfo(List<UserQuiz> userQuizList) {
        if (CollectionUtils.isEmpty(userQuizList)) {
            return Collections.emptyMap();
        }
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
                    )).entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .limit(7)
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (e1, e2) -> e1,
                            LinkedHashMap::new
                    ));
        } catch (Exception e) {
            logger.error("getWrongsInfo got exception.userID:{}", getUserId(), e);
            return Collections.emptyMap();
        }
    }

}
