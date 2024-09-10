package com.quizmarkt.base.manager;

import com.quizmarkt.base.data.entity.Quiz;
import com.quizmarkt.base.data.entity.QuizGroup;
import com.quizmarkt.base.data.repository.QuizGroupRepository;
import com.quizmarkt.base.data.repository.QuizRepository;
import com.quizmarkt.base.data.request.QuizListWithGroupIdRequest;
import com.quizmarkt.base.data.request.QuizListWithUserDataRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
    private final QuizGroupRepository quizGroupRepository;

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

    public List<Quiz> getQuizListWithGroupIdIfExist(QuizListWithGroupIdRequest request) {
        try {
            if (request.getQuizGroupId() != null) {
                QuizGroup quizGroup = new QuizGroup();
                quizGroup.setId(request.getQuizGroupId());
                return quizRepository.findAllByQuizGroupListContaining(quizGroup, PageRequest.of(request.getPage(), request.getPageSize()));
            }
            return quizRepository.findAllBy(PageRequest.of(request.getPage(), request.getPageSize()));
        } catch (Exception e) {
            logger.error("getQuizList got exception.userId:{},groupId:{}", getUserId(), e);
            return Collections.emptyList();
        }
    }

    public void saveQuizWithUpdateQuizGroup(Quiz quiz) {
        try {
            if (!CollectionUtils.isEmpty(quiz.getQuizGroupList())) {
                List<QuizGroup> quizGroups = quizGroupRepository.findAllById(quiz.getQuizGroupList().stream().map(QuizGroup::getId).toList());
                quiz.setQuizGroupList(quizGroups);
                quizRepository.save(quiz);
                for (QuizGroup quizGroup : quizGroups) {
                    int numberOfActiveQuiz = quizRepository.countAllByQuizGroupListContainingAndActive(quizGroup, true);
                    quizGroup.setQuizQuantity(numberOfActiveQuiz);
                    quizGroupRepository.save(quizGroup);
                }
            } else {
                quizRepository.save(quiz);
            }
        } catch (Exception e) {
            logger.error("saveQuizGroup got exception request:{}", quiz, e);
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
