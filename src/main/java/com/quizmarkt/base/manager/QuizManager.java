package com.quizmarkt.base.manager;

import com.quizmarkt.base.data.cache.QuizResponseInListViewCacheable;
import com.quizmarkt.base.data.cache.QuizResponseQuestionsSortedCacheable;
import com.quizmarkt.base.data.constant.CacheConstants;
import com.quizmarkt.base.data.entity.Quiz;
import com.quizmarkt.base.data.entity.QuizGroup;
import com.quizmarkt.base.data.mapper.QuizMapper;
import com.quizmarkt.base.data.repository.QuizRepository;
import com.quizmarkt.base.data.request.QuizListWithUserDataRequest;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
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
    private final QuizMapper quizMapper;

    public List<Quiz> getActiveQuizListWithGroupId(QuizListWithUserDataRequest request) { //todo pageable
        try {
            QuizGroup quizGroup = new QuizGroup();
            quizGroup.setId(request.getQuizGroupId());
            return quizRepository.findAllByQuizGroupListContainingAndActiveOrderByPriorityAsc(quizGroup, true);
        } catch (Exception e) {
            logger.error("getQuizListWithGroupId got exception.userId:{},groupId:{}", getUserId(), e);
            return Collections.emptyList();
        }
    }

    public Optional<Quiz> getQuizWithActiveQuestionsSorted(Long quizId) {
        try {
            return quizRepository.findQuizWithActiveQuestionsSorted(quizId);
        } catch (Exception e) {
            logger.error("getQuizWithId got exception.userId:{}", getUserId(), e);
            return Optional.empty();
        }
    }

    public int getActiveQuizCount() {
        try {
            return quizRepository.countAllByActiveAndAppId(true, getAppId());
        } catch (Exception e) {
            logger.error("getActiveQuizCount got exception", e);
            return 0;
        }
    }

    @Cacheable(value = CacheConstants.QUIZ, key = "#quizId", unless = "#result == null")
    public QuizResponseQuestionsSortedCacheable getQuizWithIdSorted(Long quizId) {
        Optional<Quiz> quizResponseOpt = getQuizWithActiveQuestionsSorted(quizId);
        return quizResponseOpt.map(quizMapper::toQuizResponse).orElse(null);
    }

    @Cacheable(value = CacheConstants.QUIZ_LIST, key = "#request.quizGroupId", unless = "#result == null || #result.isEmpty()")
    public List<QuizResponseInListViewCacheable> getQuizResponseWithUserDataList(QuizListWithUserDataRequest request) {
        List<Quiz> quizList = getActiveQuizListWithGroupId(request);
        return quizMapper.toQuizResponseInListViewCacheable(quizList);
    }
}
