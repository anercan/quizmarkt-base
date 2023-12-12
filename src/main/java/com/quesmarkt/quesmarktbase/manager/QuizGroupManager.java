package com.quesmarkt.quesmarktbase.manager;

import com.quesmarkt.quesmarktbase.data.entity.QuizGroup;
import com.quesmarkt.quesmarktbase.data.repository.QuizGroupRepository;
import com.quesmarkt.quesmarktbase.data.request.QuizGroupRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
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

    public List<QuizGroup> getQuizGroups(QuizGroupRequest request) {
        try {
            return quizGroupRepository.findAllByActiveAndAppId(true, getAppId(), PageRequest.of(request.getPage(), request.getPageSize()));
        } catch (Exception e) {
            logger.error("getQuizGroups got exception request:{}", request.toString(), e);
            return Collections.emptyList();
        }
    }
}
