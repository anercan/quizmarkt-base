package com.quizmarkt.base.service;

import com.quizmarkt.base.data.entity.Quiz;
import com.quizmarkt.base.data.entity.UserQuiz;
import com.quizmarkt.base.data.mapper.QuizMapper;
import com.quizmarkt.base.data.mapper.UserQuizMapper;
import com.quizmarkt.base.data.repository.QuizRepository;
import com.quizmarkt.base.data.request.CreateOrUpdateQuiz;
import com.quizmarkt.base.data.request.QuizListWithGroupIdRequest;
import com.quizmarkt.base.data.request.QuizListWithUserDataRequest;
import com.quizmarkt.base.data.response.QuizListResponse;
import com.quizmarkt.base.data.response.QuizResponse;
import com.quizmarkt.base.data.response.QuizResponseWithUserData;
import com.quizmarkt.base.manager.QuizManager;
import com.quizmarkt.base.manager.UserQuizManager;
import lombok.AllArgsConstructor;
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
public class QuizService {

    private final QuizManager quizManager;
    private final UserQuizManager userQuizManager;
    private final QuizMapper quizMapper;
    private final UserQuizMapper userQuizMapper;
    private final QuizRepository quizRepository;

    public ResponseEntity<QuizListResponse> getQuizListWithUserData(QuizListWithUserDataRequest request) {
        List<Quiz> quizList = quizManager.getActiveQuizListWithGroupId(request);
        Map<Long, UserQuiz> quizIdUserQuizMap = userQuizManager.getQuizIdAndSolvedQuestionCountMap(request.getQuizGroupId());
        List<QuizResponseWithUserData> quizWithUserDataList = quizList.stream().map(quiz -> quizMapper.getQuizResponseWithUserData(quizIdUserQuizMap, quiz)).collect(Collectors.toList());
        return ResponseEntity.ok(QuizListResponse.builder().quizResponseWithUserDataList(quizWithUserDataList).build());
    }

    public ResponseEntity<QuizResponse> getQuizWithUserQuizDataForStartTest(Long quizId) { // todo check joins entity graph
        Optional<Quiz> quizWithId = quizManager.getQuizWithId(quizId);
        if (quizWithId.isEmpty()) {
            return ResponseEntity.internalServerError().build();
        }
        QuizResponse quizResponse = quizMapper.toQuizResponse(quizWithId.get());
        Optional<UserQuiz> userQuizOptional = userQuizManager.getUserQuizWithQuizIdAndUserId(quizId);
        userQuizOptional.ifPresent(userQuiz -> quizResponse.setUserQuiz(userQuizMapper.toUserQuizResponse(userQuiz)));
        return ResponseEntity.ok(quizResponse);
    }

    public ResponseEntity<List<Quiz>> getQuizListWithGroupIfExist(QuizListWithGroupIdRequest request) {
        return ResponseEntity.ok(quizManager.getQuizListWithGroupIdIfExist(request));
    }

    public ResponseEntity<Void> saveQuiz(CreateOrUpdateQuiz request) {
        Optional<Quiz> optionalQuiz = request.getId() != null ? quizRepository.findById(request.getId()) : Optional.empty();
        Optional<Quiz> quiz = quizMapper.toQuizEntity(request, optionalQuiz);
        quiz.ifPresent(quizManager::saveQuizWithUpdateQuizGroup);
        return ResponseEntity.ok().build();
    }
}
