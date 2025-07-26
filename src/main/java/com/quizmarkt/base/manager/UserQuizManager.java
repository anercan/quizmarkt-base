package com.quizmarkt.base.manager;

import com.quizmarkt.base.data.constant.CacheConstants;
import com.quizmarkt.base.data.entity.Question;
import com.quizmarkt.base.data.entity.Quiz;
import com.quizmarkt.base.data.entity.UserQuiz;
import com.quizmarkt.base.data.entity.UserWrongAnswer;
import com.quizmarkt.base.data.enums.UserQuizState;
import com.quizmarkt.base.data.mapper.UserQuizMapper;
import com.quizmarkt.base.data.repository.UserQuizRepository;
import com.quizmarkt.base.data.request.CreateUpdateUserQuizRequest;
import com.quizmarkt.base.util.UserQuizUtil;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author anercan
 */

@AllArgsConstructor
@Service
public class UserQuizManager extends BaseManager {

    private final UserQuizRepository userQuizRepository;
    private final UserQuizMapper userQuizMapper;
    private final CacheProviderManager cacheProviderManager;

    public Map<Long, Integer> getUserQuizGroupIdQuizCountMap(Set<Long> quizGroupIdList) {
        try {
            return getAllByAppIdAndUserIdAndQuizGroupIdIn(quizGroupIdList)
                    .stream()
                    .filter(userQuiz -> UserQuizState.COMPLETED.equals(userQuiz.getState()))
                    .collect(Collectors.groupingBy(UserQuiz::getQuizGroupId, Collectors.summingInt(e -> 1)));
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
        try { //todo if completed skip for performance
            List<UserQuiz> userQuizListInQuizGroup = getAllByAppIdAndUserIdAndQuizGroupIdIn(Collections.singleton(quizGroupId));
            return userQuizListInQuizGroup.stream().collect(Collectors.toMap(UserQuiz::getQuizId, Function.identity()));
        } catch (Exception e) {
            logger.error("getQuizIdAndSolvedQuestionCountMap got exception.userId:{},quizGroupId:{}", getUserId(), quizGroupId, e);
            return Collections.emptyMap();
        }
    }

    public Optional<UserQuiz> getUserQuizWithQuizIdAndUserId(Long quizId) {
        try {
            return userQuizRepository.findByQuiz_IdAndUserId(quizId, getUserId()); //todo cachelersek getQuizWithUserQuizDataForStartTest evict etmeli gibi
        } catch (Exception e) {
            logger.error("getUserQuizWithQuizIdAndUserId got exception.userId:{} quizId:{}", getUserId(), quizId, e);
            return Optional.empty();
        }
    }

    public Optional<UserQuiz> getUserQuizWithQuizIdAndUserIdCompleted(Long quizId) {
        try {
            return userQuizRepository.findByQuiz_IdAndUserIdAndState(quizId, getUserId(),UserQuizState.COMPLETED);
        } catch (Exception e) {
            logger.error("getUserQuizWithQuizIdAndUserIdCompleted got exception.userId:{} quizId:{}", getUserId(), quizId, e);
            return Optional.empty();
        }
    }

    /**
     * Returns a list of UserQuiz filtered by quizId.
     * Uses DB index on quiz_id for faster queries.
     *
     * @param quizId the quiz ID to filter by
     * @return list of UserQuiz for the given quizId
     */
    public List<UserQuiz> getUserQuizWithQuizId(Long quizId) {
        try {
            return userQuizRepository.findByQuiz_Id(quizId);
        } catch (Exception e) {
            logger.error("getUserQuizWithQuizId got exception.userId:{} quizId:{}", getUserId(), quizId, e);
            return Collections.emptyList();
        }
    }

    public List<UserQuiz> getOrderedUserQuizList() {
        try {
            return userQuizRepository.findAllByAppIdAndUserIdOrderByCompleteDateDesc(getAppId(), getUserId());
        } catch (Exception e) {
            logger.error("getUserQuizList got exception.userId:{} appId:{}", getUserId(), getAppId(), e);
            return Collections.emptyList();
        }
    }

    @Cacheable(value = CacheConstants.USER_QUIZ_FOR_ANALYTICS, key = "#userId") //todo bu cache de proxy objeye atılmalı
    public List<UserQuiz> getUserQuizList(String userId) {
        try {
            return userQuizRepository.findAllByAppIdAndUserId(getAppId(), getUserId());
        } catch (Exception e) {
            logger.error("getUserQuizList got exception.userId:{} appId:{}", getUserId(), getAppId(), e);
            return Collections.emptyList();
        }
    }

    public long getUserQuizCount(UserQuizState userQuizState) {
        try {
            return userQuizRepository.countByAppIdAndUserIdAndState(getAppId(), getUserId(),userQuizState);
        } catch (Exception e) {
            logger.error("getUserQuizCount got exception.userId:{} appId:{}", getUserId(), getAppId(), e);
            return 0;
        }
    }

    public UserQuiz createNewUserQuiz(CreateUpdateUserQuizRequest request) {
        try {
            UserQuiz userQuiz = new UserQuiz();
            Quiz quiz = new Quiz();
            quiz.setId(request.getQuizId());
            userQuiz.setQuiz(quiz);
            userQuiz.setUserId(getUserId());
            userQuiz.setStartDate(ZonedDateTime.now());
            userQuiz.setQuizGroupId(request.getQuizGroupId());
            userQuiz.setAppId(getAppId());
            userQuiz.setState(UserQuizState.ON_GOING);
            UserQuiz createdUserQuiz = userQuizRepository.save(userQuiz);
            clearUserRelatedCache();
            logger.info("userQuiz created userId:{} appId:{} quizId:{}", getUserId(), getAppId(), request.getQuizId());
            return createdUserQuiz;
        } catch (Exception e) {
            logger.error("createNewUserQuiz got exception.userId:{} appId:{} quizId:{}", getUserId(), getAppId(), request.getQuizId(), e);
            return null;
        }
    }

    private void clearUserRelatedCache() {
        cacheProviderManager.evictUserQuizListCache(getUserId(), getAppId());
        cacheProviderManager.evictUserDataCache(getUserId());
        cacheProviderManager.evictUserQuizForAnalyticsCache(getUserId());
    }

    public UserQuiz updateUserQuiz(CreateUpdateUserQuizRequest request, UserQuiz userQuiz) {
        try {
            if (isUpdatedBefore(request, userQuiz)) {
                logger.warn("This question answered before.request:{}", request);
                return null;
            }
            boolean isCorrectAnswer = Objects.nonNull(request.getCorrectQuestionId()) && request.getCorrectQuestionId() != 0;
            boolean isWrongAnswer = Objects.nonNull(request.getUserWrongAnswerRequest());
            if (isCorrectAnswer) {
                userQuiz.getCorrectQuestionList().add(request.getCorrectQuestionId());
            } else if (isWrongAnswer) {
                userQuiz.getWrongQuestionList().add(userQuizMapper.getUserWrongAnswerFromCreateOrUpdateRequest(request));
            }
            if (UserQuizUtil.isQuizCompleted(userQuiz)) {
                userQuiz.setState(UserQuizState.COMPLETED);
                userQuiz.setCompleteDate(ZonedDateTime.now());
            }
            UserQuiz updatedQuiz = userQuizRepository.save(userQuiz);
            clearUserRelatedCache();
            logger.debug("userQuiz updated userId:{} appId:{} quizId:{}", getUserId(), getAppId(), request.getQuizId());
            return updatedQuiz;
        } catch (Exception e) {
            logger.error("updateUserQuiz got exception.userId:{} appId:{} quizId:{}", getUserId(), getAppId(), request.getQuizId(), e);
            return null;
        }
    }

    private boolean isUpdatedBefore(CreateUpdateUserQuizRequest request, UserQuiz userQuiz) {
        List<Long> wrongQuestionIdSet = userQuiz.getWrongQuestionList().stream().map(UserWrongAnswer::getQuestion).map(Question::getId).collect(Collectors.toList());
        List<Long> correctQuestionIdSet = userQuiz.getCorrectQuestionList();
        List<Long> combinedQuestionIdSet = new ArrayList<>(wrongQuestionIdSet); // Initialize with wrong questions
        combinedQuestionIdSet.addAll(correctQuestionIdSet);
        Long selectedQuestionId = request.getUserWrongAnswerRequest() != null ? request.getUserWrongAnswerRequest().getQuestionId() : request.getCorrectQuestionId();
        return combinedQuestionIdSet.contains(selectedQuestionId);
    }
}
