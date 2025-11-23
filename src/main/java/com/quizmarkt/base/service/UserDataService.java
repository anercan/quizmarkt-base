package com.quizmarkt.base.service;

import com.quizmarkt.base.data.cache.UserDataResponse;
import com.quizmarkt.base.data.cache.UserQuizCacheable;
import com.quizmarkt.base.data.cache.UserWrongAnswerCacheable;
import com.quizmarkt.base.data.constant.CacheConstants;
import com.quizmarkt.base.data.enums.UserQuizState;
import com.quizmarkt.base.data.response.ActivityData;
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

    @Cacheable(value = CacheConstants.USER_DATA, key = "#userId")
    public UserDataResponse getUserData(Optional<UserInfo> userInfo, String userId) {
        return UserDataResponse.builder()
                .totalQuizCount(quizManager.getActiveQuizCount())
                .userSolvedQuizCount(userQuizManager.getUserQuizCount(UserQuizState.COMPLETED))
                .userOngoingQuizCount(userQuizManager.getUserQuizCount(UserQuizState.ON_GOING))
                .avatarUrl(userInfo.map(UserInfo::getAvatarUrl).orElse(null))
                .build();
    }

    public List<ActivityData> getActivityData(List<UserQuizCacheable> userQuizList) {
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

    public Map<String, Integer> getWrongsInfo(List<UserQuizCacheable> userQuizList) {
        if (CollectionUtils.isEmpty(userQuizList)) {
            return Collections.emptyMap();
        }
        try {
            return userQuizList.stream()
                    .flatMap(userQuiz ->
                            userQuiz.getWrongQuestionList()
                                    .stream()
                                    .map(UserWrongAnswerCacheable::getQuestion)
                    ).filter(question -> StringUtils.isNotEmpty(question.getAttributes().get("subject")))
                    .collect(Collectors.toMap(
                            question -> question.getAttributes().get("subject"),
                            question -> 1,
                            Integer::sum
                    )).entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .limit(10)
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
