package com.quizmarkt.base.manager;

import com.quizmarkt.base.data.entity.QuizGroup;
import com.quizmarkt.base.data.repository.QuizGroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author anercan
 */

@AllArgsConstructor
@Service
public class QuizGroupManager extends BaseManager {

    private final QuizGroupRepository quizGroupRepository;

    public List<QuizGroup> getActiveQuizGroups(com.quizmarkt.base.data.request.PageRequest request) {
        try {
            return quizGroupRepository.findAllByActiveAndAppIdOrderByPriorityAsc(true, getAppId());
        } catch (Exception e) {
            logger.error("getQuizGroups got exception request:{}", request.toString(), e);
            return Collections.emptyList();
        }
    }
}
