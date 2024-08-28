package com.quesmarkt.quesmarktbase.manager;

import com.quesmarkt.quesmarktbase.data.entity.Quiz;
import com.quesmarkt.quesmarktbase.data.entity.QuizGroup;
import com.quesmarkt.quesmarktbase.data.repository.QuizRepository;
import com.quesmarkt.quesmarktbase.data.request.QuizListWithUserDataRequest;
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
            return quizRepository.findAllByQuizGroupListContainingAndActive(quizGroup, true, PageRequest.of(request.getPage(), request.getPageSize()));
        } catch (Exception e) {
            logger.error("getQuizListWithGroupId got exception.userId:{},groupId:{}", getUserId(), e);
            return Collections.emptyList();
        }
    }

    public Optional<Quiz> getQuizWithId(Long quizId) {
        try {
            return quizRepository.findById(quizId);
        } catch (Exception e) {
            logger.error("getWithId got exception.userId:{}", getUserId(), e);
            return Optional.empty();
        }
    }

    public List<Quiz> getQuizList(com.quesmarkt.quesmarktbase.data.request.PageRequest request) {
        try {
            return quizRepository.findAllBy(PageRequest.of(request.getPage(), request.getPageSize()));
        } catch (Exception e) {
            logger.error("getQuizList got exception.userId:{},groupId:{}", getUserId(), e);
            return Collections.emptyList();
        }
    }
}
