package com.quesmarkt.quesmarktbase.manager;

import com.quesmarkt.quesmarktbase.data.entity.QuizGroup;
import com.quesmarkt.quesmarktbase.data.repository.QuizGroupRepository;
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

    public List<QuizGroup> getActiveQuizGroups(com.quesmarkt.quesmarktbase.data.request.PageRequest request) {
        try {
            return quizGroupRepository.findAllByActiveAndAppId(true, getAppId(), PageRequest.of(request.getPage(), request.getPageSize()));
        } catch (Exception e) {
            logger.error("getQuizGroups got exception request:{}", request.toString(), e);
            return Collections.emptyList();
        }
    }

    public List<QuizGroup> getQuizGroups(com.quesmarkt.quesmarktbase.data.request.PageRequest request) {
        try {
            return quizGroupRepository.findAllByAppId( getAppId(), PageRequest.of(request.getPage(), request.getPageSize()));
        } catch (Exception e) {
            logger.error("getQuizGroups got exception request:{}", request.toString(), e);
            return Collections.emptyList();
        }
    }

    public QuizGroup saveQuizGroup(QuizGroup quizGroup) {
        try {
            return quizGroupRepository.save(quizGroup);
        } catch (Exception e) {
            logger.error("saveQuizGroup got exception request:{}", quizGroup.toString(), e);
            return null;
        }
    }
}
