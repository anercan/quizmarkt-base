package com.quizmarkt.base.manager;

import com.quizmarkt.base.data.entity.Quiz;
import com.quizmarkt.base.data.entity.QuizGroup;
import com.quizmarkt.base.data.repository.QuizRepository;
import com.quizmarkt.base.data.request.QuizListWithUserDataRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author anercan
 */

@AllArgsConstructor
@Service
public class QuizManager extends BaseManager {

    private final QuizRepository quizRepository;

    public List<Quiz> getActiveQuizListWithGroupId(QuizListWithUserDataRequest request) {
        try {
            QuizGroup quizGroup = new QuizGroup();
            quizGroup.setId(request.getQuizGroupId());
            return quizRepository.findAllByQuizGroupListContainingAndActiveOrderByPriorityAsc(quizGroup, true, PageRequest.of(request.getPage(), request.getPageSize()));
        } catch (Exception e) {
            logger.error("getQuizListWithGroupId got exception.userId:{},groupId:{}", getUserId(), e);
            return Collections.emptyList();
        }
    }

    //todo cacheable olabilir app sayısına göre en cok acılan 20 tane vs cachede durur
    public Optional<Quiz> getQuizWithIdQuestionsSorted(Long quizId) {
        try {
            return quizRepository.findQuizWithQuestionsSorted(quizId);
        } catch (Exception e) {
            logger.error("getQuizWithIdQuestionsSorted got exception.userId:{}", getUserId(), e);
            return Optional.empty();
        }
    }

    public int getActiveQuizCount() {
        try {
            return quizRepository.countAllByActiveAndAppId(true,getAppId());
        } catch (Exception e) {
            logger.error("getActiveQuizCount got exception", e);
            return 0;
        }
    }
}
