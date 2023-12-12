package com.quesmarkt.quesmarktbase.manager;

import com.quesmarkt.quesmarktbase.data.entity.UserQuiz;
import com.quesmarkt.quesmarktbase.data.repository.UserQuizRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author anercan
 */

@AllArgsConstructor
@Service
public class UserQuizManager extends BaseManager {

    private final UserQuizRepository userQuizRepository;

    public Map<Long, Integer> getUserQuizGroupIdQuizCountMap(Set<Long> quizGroupIdList) {
        try {
            return getAllByAppIdAndUserIdAndQuizGroupIdIn(quizGroupIdList)
                    .stream().collect(Collectors.groupingBy(UserQuiz::getQuizGroupId, Collectors.summingInt(e -> 1)));
        } catch (Exception e) {
            logger.error("getUserQuizIdCountMap got exception.userId:{},quizGroupId:{}", getUserId(), quizGroupIdList, e);
            return Collections.emptyMap();
        }
    }

    private List<UserQuiz> getAllByAppIdAndUserIdAndQuizGroupIdIn(Set<Long> quizGroupIdList) {
        try {
            return userQuizRepository.findAllByAppIdAndUserIdAndQuizGroupIdIn(getAppId(), getUserId(), quizGroupIdList);
        } catch (Exception e) {
            logger.error("getQuizIdAndSolvedQuestionCountMap got exception.userId:{},quizGroupId:{}", getUserId(), quizGroupIdList, e);
            return Collections.emptyList();
        }
    }

    public Map<Long, UserQuiz> getQuizIdAndSolvedQuestionCountMap(Long quizGroupId) {
        try {
            List<UserQuiz> userQuizListInQuizGroup = getAllByAppIdAndUserIdAndQuizGroupIdIn(Collections.singleton(quizGroupId));
            return userQuizListInQuizGroup.stream().collect(Collectors.toMap(UserQuiz::getQuizId,Function.identity()));
        } catch (Exception e) {
            logger.error("getQuizIdAndSolvedQuestionCountMap got exception.userId:{},quizGroupId:{}", getUserId(), quizGroupId, e);
            return Collections.emptyMap();
        }
    }
}
