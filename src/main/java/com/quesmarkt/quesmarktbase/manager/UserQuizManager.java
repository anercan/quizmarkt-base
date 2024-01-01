package com.quesmarkt.quesmarktbase.manager;

import com.quesmarkt.quesmarktbase.data.entity.Quiz;
import com.quesmarkt.quesmarktbase.data.entity.UserQuiz;
import com.quesmarkt.quesmarktbase.data.enums.UserQuizState;
import com.quesmarkt.quesmarktbase.data.mapper.UserQuizMapper;
import com.quesmarkt.quesmarktbase.data.repository.UserQuizRepository;
import com.quesmarkt.quesmarktbase.data.request.CreateUpdateUserQuizRequest;
import com.quesmarkt.quesmarktbase.manager.exception.DataAccessException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.quesmarkt.quesmarktbase.util.UserQuizUtil.isQuizCompleted;

/**
 * @author anercan
 */

@AllArgsConstructor
@Service
public class UserQuizManager extends BaseManager {

    private final UserQuizRepository userQuizRepository;
    private final UserQuizMapper userQuizMapper;

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
            return userQuizRepository.findByQuiz_IdAndUserId(quizId, getUserId()); //todo  cache
        } catch (Exception e) {
            logger.error("getUserQuizWithQuizIdAndUserId got exception.userId:{} quizId:{}", getUserId(), quizId, e);
            return Optional.empty();
        }
    }

    public List<UserQuiz> getUserQuizList() {
        try {
            return userQuizRepository.findAllByAppIdAndUserIdOrderByCompleteDate(getAppId(), getUserId());
        } catch (Exception e) {
            logger.error("getUserQuizList got exception.userId:{} appId:{}", getUserId(), getAppId(), e);
            throw new DataAccessException(e);
        }
    }

    public UserQuiz createNewUserQuiz(CreateUpdateUserQuizRequest request) {
        try {
            UserQuiz userQuiz = new UserQuiz();
            Quiz quiz = new Quiz();
            quiz.setId(request.getQuizId());
            userQuiz.setQuiz(quiz);
            userQuiz.setUserId(getUserId());
            userQuiz.setCompleteDate(ZonedDateTime.now());
            userQuiz.setQuizGroupId(request.getQuizGroupId());
            userQuiz.setAppId(getAppId());
            userQuiz.setState(UserQuizState.ON_GOING);
            UserQuiz createdUserQuiz = userQuizRepository.save(userQuiz);
            logger.info("userQuiz created userId:{} appId:{} quizId:{}", getUserId(), getAppId(), request.getQuizId());
            return createdUserQuiz;
        } catch (Exception e) {
            logger.error("createNewUserQuiz got exception.userId:{} appId:{} quizId:{}", getUserId(), getAppId(), request.getQuizId(), e);
            return null;
        }
    }

    public UserQuiz updateUserQuiz(CreateUpdateUserQuizRequest request, UserQuiz userQuiz) {
        try {
            boolean isCorrectAnswer = Objects.nonNull(request.getCorrectQuestionId()) && request.getCorrectQuestionId() != 0;
            boolean isWrongAnswer = Objects.nonNull(request.getUserWrongAnswerRequest());
            if (isCorrectAnswer) {
                userQuiz.getCorrectQuestionList().add(request.getCorrectQuestionId());
            } else if (isWrongAnswer) {
                userQuiz.getWrongQuestionList().add(userQuizMapper.getUserWrongAnswerFromCreateOrUpdateRequest(request));
            }
            if (isQuizCompleted(userQuiz)) {
                userQuiz.setState(UserQuizState.COMPLETED);
            }
            userQuiz.setCompleteDate(ZonedDateTime.now());
            UserQuiz updatedQuiz = userQuizRepository.save(userQuiz);
            logger.info("userQuiz updated userId:{} appId:{} quizId:{}", getUserId(), getAppId(), request.getQuizId());
            return updatedQuiz;
        } catch (Exception e) {
            logger.error("updateUserQuiz got exception.userId:{} appId:{} quizId:{}", getUserId(), getAppId(), request.getQuizId(), e);
            return null;
        }
    }
}
