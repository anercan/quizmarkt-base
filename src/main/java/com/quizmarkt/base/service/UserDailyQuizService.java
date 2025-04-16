package com.quizmarkt.base.service;

import com.quizmarkt.base.data.entity.Question;
import com.quizmarkt.base.data.entity.UserDailyQuiz;
import com.quizmarkt.base.data.enums.UserQuizState;
import com.quizmarkt.base.data.mapper.QuestionMapper;
import com.quizmarkt.base.data.mapper.UserDailyQuizMapper;
import com.quizmarkt.base.data.request.SaveUserDailyQuizRequest;
import com.quizmarkt.base.data.response.ApiResponse;
import com.quizmarkt.base.data.response.QuestionResponse;
import com.quizmarkt.base.data.response.UserDailyQuizResponse;
import com.quizmarkt.base.data.response.UserDailyQuizWithInfoResponse;
import com.quizmarkt.base.manager.QuestionManager;
import com.quizmarkt.base.manager.UserDailyQuizManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author anercan
 */

@Service
@AllArgsConstructor
public class UserDailyQuizService extends BaseService {

    public static final int DAILY_QUIZ_SIZE = 15;
    private final QuestionManager questionManager;
    private final UserDailyQuizManager userDailyQuizManager;
    private final UserDailyQuizMapper userDailyQuizMapper;
    private final QuestionMapper questionMapper;

    public ApiResponse<UserDailyQuizWithInfoResponse> getUserDailyQuiz() {
        Optional<UserDailyQuiz> userDailyQuiz = userDailyQuizManager.findByAppIdAndUserIdAndCreatedDate();
        if (userDailyQuiz.isPresent()) {
            UserDailyQuizResponse userDailyQuizResponse = userDailyQuizMapper.toResponse(userDailyQuiz.get());
            List<Question> wrongQuestionList = questionManager.getQuestionsWithIdList(userDailyQuiz.get().getWrongQuestionIdList());
            userDailyQuizResponse.setWrongQuestionsSubjects(getSubjectMap(wrongQuestionList));
            return new ApiResponse<>(UserDailyQuizWithInfoResponse.builder().userDailyQuizResponse(userDailyQuizResponse).build());
        }
        if (isNonPremiumSolvedDailyQuizBefore()) {
            return new ApiResponse<>(ApiResponse.Status.notAuthorizedPremiumOperation("getUserDailyQuiz"));
        }
        initUserDailyQuiz();
        List<Question> questionList = questionManager.getRandomQuestionListWithAppId(getAppId(), DAILY_QUIZ_SIZE);
        List<QuestionResponse> questionResponseList = questionMapper.toQuestionListResponse(questionList);
        return new ApiResponse<>(UserDailyQuizWithInfoResponse.builder().questionList(questionResponseList).build());
    }

    private boolean isNonPremiumSolvedDailyQuizBefore() {
        return isNonPremium() && userDailyQuizManager.isExistByUserIdAndAppId();
    }

    public ApiResponse<Boolean> saveUserDailyQuiz(SaveUserDailyQuizRequest request) {
        Optional<UserDailyQuiz> userDailyQuiz = userDailyQuizManager.findByAppIdAndUserIdAndCreatedDate();
        if (userDailyQuiz.isPresent() && UserQuizState.COMPLETED.equals(userDailyQuiz.get().getState())) {
            return new ApiResponse<>(Boolean.TRUE);
        }
        UserDailyQuiz entity = userDailyQuiz.orElseGet(UserDailyQuiz::new);
        entity.setAppId(getAppId());
        entity.setUserId(getUserId());
        if (!Objects.isNull(request.getCorrectQuestionId())) {
            entity.getCorrectQuestionIdList().add(request.getCorrectQuestionId());
        }
        if (!Objects.isNull(request.getWrongQuestionId())) {
            entity.getWrongQuestionIdList().add(request.getWrongQuestionId());
        }
        entity.setState(getQuizState(entity));
        entity.setCreatedDate(entity.getCreatedDate() != null ? entity.getCreatedDate() : LocalDate.now());
        boolean save = userDailyQuizManager.save(entity);
        return new ApiResponse<>(save);
    }

    private UserQuizState getQuizState(UserDailyQuiz entity) {
        int correctSize = CollectionUtils.isEmpty(entity.getCorrectQuestionIdList()) ? 0 : entity.getCorrectQuestionIdList().size();
        int wrongSize = CollectionUtils.isEmpty(entity.getWrongQuestionIdList()) ? 0 : entity.getWrongQuestionIdList().size();
        return DAILY_QUIZ_SIZE == correctSize + wrongSize ? UserQuizState.COMPLETED : UserQuizState.ON_GOING;
    }

    private void initUserDailyQuiz() {
        saveUserDailyQuiz(SaveUserDailyQuizRequest.builder().build());
    }

    private Map<String, Long> getSubjectMap(List<Question> wrongQuestionList) {
        return wrongQuestionList.stream()
                .map(Question::getAttributes)
                .map(map -> map.get("subject"))
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }
}
