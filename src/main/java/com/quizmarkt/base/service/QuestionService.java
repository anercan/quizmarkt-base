package com.quizmarkt.base.service;

import com.quizmarkt.base.data.entity.Question;
import com.quizmarkt.base.data.entity.Quiz;
import com.quizmarkt.base.data.mapper.QuestionMapper;
import com.quizmarkt.base.data.repository.QuestionRepository;
import com.quizmarkt.base.data.repository.QuizRepository;
import com.quizmarkt.base.data.request.CreateOrUpdateAnswer;
import com.quizmarkt.base.data.request.CreateOrUpdateQuestion;
import com.quizmarkt.base.manager.QuestionManager;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author anercan
 */

@Service
@AllArgsConstructor
public class QuestionService extends BaseService {

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;
    private final QuestionMapper questionMapper;
    private final QuestionManager questionManager;

    public ResponseEntity<List<Question>> getQuestionList(Long quizId) {
        Optional<Quiz> optQuiz = quizRepository.findById(quizId);
        return optQuiz.map(quiz -> ResponseEntity.ok(quiz.getQuestionList())).orElseGet(() -> ResponseEntity.ok(Collections.emptyList()));
    }

    public ResponseEntity<Void> saveQuestion(CreateOrUpdateQuestion request) {
        Optional<Question> optionalQuestion = request.getId() != null ? questionRepository.findById(request.getId()) : Optional.empty();
        Optional<Question> question = questionMapper.toQuestionEntity(request, optionalQuestion);
        question.ifPresent(q -> questionManager.saveQuestionWithAnswers(q, request.getQuizId(), request.getCreateOrUpdateAnswerList().stream().filter(CreateOrUpdateAnswer::isCorrectAnswer).findFirst()));
        return ResponseEntity.ok().build();
    }
}
