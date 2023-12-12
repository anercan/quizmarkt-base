package com.quesmarkt.quesmarktbase.manager;

import com.quesmarkt.quesmarktbase.data.entity.UserQuiz;
import com.quesmarkt.quesmarktbase.data.repository.UserQuizRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author anercan
 */

@AllArgsConstructor
@Service
public class UserQuizManager extends BaseManager {

    private final UserQuizRepository userQuizRepository;

    public Map<Long, Integer> getUserQuizIdCountMap(Set<Long> quizGroupIdList) {
        try {
            return userQuizRepository.findAllByAppIdAndUserIdAndQuizGroupIdIn(getAppId(), getUserId(), quizGroupIdList)
                    .stream().collect(Collectors.groupingBy(UserQuiz::getQuizGroupId, Collectors.summingInt(e -> 1)));
        } catch (Exception e) {
            logger.error("getUserQuizIdCountMap got exception.userId:{},quizGroupId:{}", getUserId(), quizGroupIdList, e);
            return Collections.emptyMap();
        }
    }
}
