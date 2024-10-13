package com.quizmarkt.base.service;

import com.quizmarkt.base.data.entity.Answer;
import com.quizmarkt.base.data.entity.Question;
import com.quizmarkt.base.data.entity.Quiz;
import com.quizmarkt.base.data.entity.QuizGroup;
import com.quizmarkt.base.data.mapper.QuestionMapper;
import com.quizmarkt.base.data.mapper.QuizGroupMapper;
import com.quizmarkt.base.data.mapper.QuizMapper;
import com.quizmarkt.base.data.repository.QuestionRepository;
import com.quizmarkt.base.data.repository.QuizGroupRepository;
import com.quizmarkt.base.data.repository.QuizRepository;
import com.quizmarkt.base.data.request.PageRequest;
import com.quizmarkt.base.data.request.QuizListWithGroupIdRequest;
import com.quizmarkt.base.data.request.admin.CreateOrUpdateAnswer;
import com.quizmarkt.base.data.request.admin.CreateOrUpdateQuestion;
import com.quizmarkt.base.data.request.admin.CreateOrUpdateQuiz;
import com.quizmarkt.base.data.request.admin.CreateOrUpdateQuizGroup;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author anercan
 */
// TODO: 13.10.2024 this service can be external microservice

@Service
@AllArgsConstructor
public class AdminCRUDService {

    protected static final Logger logger = LoggerFactory.getLogger(AdminCRUDService.class);

    private final QuizRepository quizRepository;
    private final QuizGroupRepository quizGroupRepository;
    private final QuizMapper quizMapper;
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final QuizGroupMapper quizGroupMapper;

    public ResponseEntity<List<Quiz>> getQuizListWithGroupIfExist(QuizListWithGroupIdRequest request) {
        return ResponseEntity.ok(getQuizListWithGroupIdIfExist(request));
    }

    public ResponseEntity<Void> saveQuiz(CreateOrUpdateQuiz request) {
        Optional<Quiz> optionalQuiz = request.getId() != null ? quizRepository.findById(request.getId()) : Optional.empty();
        Optional<Quiz> quiz = quizMapper.toQuizEntity(request, optionalQuiz);
        quiz.ifPresent(this::saveQuizWithUpdateQuizGroup);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<List<QuizGroup>> getQuizGroupList(@RequestBody PageRequest request) {
        List<QuizGroup> quizGroupList = getQuizGroups(request);
        return ResponseEntity.ok(quizGroupList);
    }

    public ResponseEntity<Void> saveQuizGroup(CreateOrUpdateQuizGroup request) {
        Optional<QuizGroup> optionalQuizGroup = request.getId() != null ? quizGroupRepository.findById(request.getId()) : Optional.empty();
        Optional<QuizGroup> quizGroup = quizGroupMapper.toQuizGroupEntity(request, optionalQuizGroup);
        quizGroup.ifPresent(this::saveQuizGroup);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<List<Question>> getQuestionList(Long quizId) {
        Optional<Quiz> optQuiz = quizRepository.findById(quizId);
        return optQuiz.map(quiz -> ResponseEntity.ok(quiz.getQuestionList())).orElseGet(() -> ResponseEntity.ok(Collections.emptyList()));
    }

    public ResponseEntity<Void> saveQuestion(CreateOrUpdateQuestion request) {
        Optional<Question> optionalQuestion = request.getId() != null ? questionRepository.findById(request.getId()) : Optional.empty();
        Optional<Question> question = questionMapper.toQuestionEntity(request, optionalQuestion);
        question.ifPresent(q -> this.saveQuestionWithAnswers(q, request.getQuizId(), request.getCreateOrUpdateAnswerList().stream().filter(CreateOrUpdateAnswer::isCorrectAnswer).findFirst()));
        return ResponseEntity.ok().build();
    }

    public List<Quiz> getQuizListWithGroupIdIfExist(QuizListWithGroupIdRequest request) {
        try {
            if (request.getQuizGroupId() != null) {
                QuizGroup quizGroup = new QuizGroup();
                quizGroup.setId(request.getQuizGroupId());
                return quizRepository.findAllByQuizGroupListContaining(quizGroup, org.springframework.data.domain.PageRequest.of(request.getPage(), request.getPageSize()));
            }
            return quizRepository.findAllBy(org.springframework.data.domain.PageRequest.of(request.getPage(), request.getPageSize()));
        } catch (Exception e) {
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

    public List<QuizGroup> getQuizGroups(com.quizmarkt.base.data.request.PageRequest request) {
        try {
            return quizGroupRepository.findAllByAppId(request.getAppId(), org.springframework.data.domain.PageRequest.of(request.getPage(), request.getPageSize()));
        } catch (Exception e) {
            logger.error("getQuizGroups got exception request:{}", request.toString(), e);
            return Collections.emptyList();
        }
    }

    public QuizGroup saveQuizGroup(QuizGroup quizGroup) {
        try {
            if (quizGroup.getId() != null) {
                int numberOfActiveQuiz = quizRepository.countAllByQuizGroupListContainingAndActive(quizGroup, true);
                quizGroup.setQuizQuantity(numberOfActiveQuiz);
            }
            return quizGroupRepository.save(quizGroup);
        } catch (Exception e) {
            logger.error("saveQuizGroup got exception request:{}", quizGroup.toString(), e);
            return null;
        }
    }


    public void saveQuestionWithAnswers(Question question, Long quizId, Optional<CreateOrUpdateAnswer> correctAnswer) {
        Question savedQuestion = questionRepository.save(question);
        if (correctAnswer.isPresent()) {
            setCorrectAnswerId(correctAnswer, savedQuestion);
        }
        addToQuiz(quizId, savedQuestion);
    }

    private void setCorrectAnswerId(Optional<CreateOrUpdateAnswer> correctAnswer, Question savedQuestion) {
        Long correctId = savedQuestion.getAnswersList()
                .stream()
                .filter(answer -> answer.getContent().equalsIgnoreCase(correctAnswer.get().getContent()))
                .map(Answer::getId)
                .findFirst()
                .orElse(null);
        savedQuestion.setCorrectAnswerId(correctId);
        questionRepository.save(savedQuestion);
    }

    private void addToQuiz(Long quizId, Question savedQuestion) {
        Optional<Quiz> quizOpt = quizRepository.findById(quizId);
        if (quizOpt.isPresent()) {
            Quiz quiz = quizOpt.get();
            boolean notExistInQuiz = quiz.getQuestionList().stream().noneMatch(q -> savedQuestion.getId().equals(q.getId()));
            if (notExistInQuiz) {
                if (quiz.getQuestionList().size() > 0) {
                    quiz.getQuestionList().add(savedQuestion);
                } else {
                    List<Question> questionList = new ArrayList<>();
                    questionList.add(savedQuestion);
                    quiz.setQuestionList(questionList);
                }
            }
            quiz.setActiveQuestionCount(Integer.parseInt(quiz.getQuestionList().stream().filter(Question::isActive).count() + ""));
            quizRepository.save(quiz);
        }
    }

}
