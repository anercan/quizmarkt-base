package com.quizmarkt.base.manager;

import com.quizmarkt.base.data.entity.QuizGroup;
import com.quizmarkt.base.data.repository.QuizGroupRepository;
import com.quizmarkt.base.data.repository.QuizRepository;
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
    private final QuizRepository quizRepository;

    public List<QuizGroup> getActiveQuizGroups(com.quizmarkt.base.data.request.PageRequest request) {
        try {
            return quizGroupRepository.findAllByActiveAndAppId(true, getAppId(), PageRequest.of(request.getPage(), request.getPageSize()));
        } catch (Exception e) {
            logger.error("getQuizGroups got exception request:{}", request.toString(), e);
            return Collections.emptyList();
        }
    }

    public List<QuizGroup> getQuizGroups(com.quizmarkt.base.data.request.PageRequest request) {
        try {
            return quizGroupRepository.findAllByAppId(getAppId(), PageRequest.of(request.getPage(), request.getPageSize()));
        } catch (Exception e) {
            logger.error("getQuizGroups got exception request:{}", request.toString(), e);
            return Collections.emptyList();
        }
    }

    public QuizGroup saveQuizGroup(QuizGroup quizGroup) {
        try {
            if (quizGroup.getId() != null) {
                int numberOfActiveQuiz = quizRepository.countAllByQuizGroupListContainingAndActive(quizGroup, true);
                quizGroup.setQuizQuantity(numberOfActiveQuiz);
            }
            return quizGroupRepository.save(quizGroup);
        } catch (Exception e) {
            logger.error("saveQuizGroup got exception request:{}", quizGroup.toString(), e);
            return null;
        }
    }
}
