package com.quizmarkt.base.service;

import com.quizmarkt.base.data.entity.Quiz;
import com.quizmarkt.base.data.entity.UserQuiz;
import com.quizmarkt.base.data.mapper.QuizMapper;
import com.quizmarkt.base.data.mapper.UserQuizMapper;
import com.quizmarkt.base.data.request.QuizListWithUserDataRequest;
import com.quizmarkt.base.data.response.QuizListResponse;
import com.quizmarkt.base.data.response.QuizResponse;
import com.quizmarkt.base.data.response.QuizResponseWithUserData;
import com.quizmarkt.base.manager.QuizManager;
import com.quizmarkt.base.manager.UserQuizManager;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author anercan
 */

@Service
@AllArgsConstructor
public class QuizService extends BaseService {

    private final QuizManager quizManager;
    private final UserQuizManager userQuizManager;
    private final QuizMapper quizMapper;
    private final UserQuizMapper userQuizMapper;

    public ResponseEntity<QuizListResponse> getQuizListWithUserData(QuizListWithUserDataRequest request) {
        List<Quiz> quizList = quizManager.getActiveQuizListWithGroupId(request);
        Map<Long, UserQuiz> quizIdUserQuizMap = userQuizManager.getQuizIdAndSolvedQuestionCountMap(request.getQuizGroupId());
        List<QuizResponseWithUserData> quizWithUserDataList = quizList.stream().map(quiz -> quizMapper.getQuizResponseWithUserData(quizIdUserQuizMap, quiz, getPremiumType())).collect(Collectors.toList());
        return ResponseEntity.ok(QuizListResponse.builder().quizResponseWithUserDataList(quizWithUserDataList).build());
    }

    public ResponseEntity<QuizResponse> getQuizWithUserQuizDataForStartTest(Long quizId) {
        Optional<Quiz> quizWithId = quizManager.getQuizWithIdQuestionsSorted(quizId);
        if (quizWithId.isEmpty()) {
            return ResponseEntity.internalServerError().build();
        }
        if (!quizWithId.get().getAvailablePremiumTypes().contains(getPremiumType())) {
            logger.warn("getQuizWithUserQuizDataForStartTest without correct premium info.userId:{} quizId:{}", getUserId(), quizId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        QuizResponse quizResponse = quizMapper.toQuizResponse(quizWithId.get());
        Optional<UserQuiz> userQuizOptional = userQuizManager.getUserQuizWithQuizIdAndUserId(quizId);
        userQuizOptional.ifPresent(userQuiz -> quizResponse.setUserQuiz(userQuizMapper.toUserQuizResponse(userQuiz)));
        return ResponseEntity.ok(quizResponse);
    }
}
