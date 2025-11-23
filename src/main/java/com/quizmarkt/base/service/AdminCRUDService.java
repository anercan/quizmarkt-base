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
import com.quizmarkt.base.data.repository.UserQuizRepository;
import com.quizmarkt.base.data.request.PageRequest;
import com.quizmarkt.base.data.request.QuizListWithGroupIdRequest;
import com.quizmarkt.base.data.request.SignInRequest;
import com.quizmarkt.base.data.request.UserFilterRequest;
import com.quizmarkt.base.data.request.admin.*;
import com.quizmarkt.base.data.response.JwtResponse;
import com.quizmarkt.base.data.response.UserResponse;
import com.quizmarkt.base.manager.CacheProviderManager;
import com.quizmarkt.base.manager.UserManagementManager;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author anercan
 */
// TODO: 13.10.2024 this service can be external microservice

@Service
@AllArgsConstructor
public class AdminCRUDService extends BaseAppSupport {

    protected static final Logger logger = LoggerFactory.getLogger(AdminCRUDService.class);

    private final QuizRepository quizRepository;
    private final QuizGroupRepository quizGroupRepository;
    private final QuizMapper quizMapper;
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final QuizGroupMapper quizGroupMapper;
    private final CacheProviderManager cacheProviderManager;
    private final UserManagementService userManagementService;
    private final UserManagementManager userManagementManager;
    private final UserQuizRepository userQuizRepository;

    public ResponseEntity<JwtResponse> adminLogin(SignInRequest request) {
        ResponseEntity<JwtResponse> response = userManagementService.adminLogin(request);
        return ResponseEntity.ok(response.getBody());
    }

    public ResponseEntity<Void> saveQuiz(CreateOrUpdateQuiz request) {
        cacheProviderManager.evictQuizRelatedCaches();
        Optional<Quiz> optionalQuiz = request.getId() != null ? quizRepository.findById(request.getId()) : Optional.empty();
        Optional<Quiz> quiz = quizMapper.toQuizEntity(request, optionalQuiz);
        quiz.ifPresent(this::saveQuizWithUpdateQuizGroup);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> saveQuizGroup(CreateOrUpdateQuizGroup request) {
        cacheProviderManager.evictQuizGroupRelatedCaches();
        Optional<QuizGroup> optionalQuizGroup = request.getId() != null ? quizGroupRepository.findById(request.getId()) : Optional.empty();
        Optional<QuizGroup> quizGroup = quizGroupMapper.toQuizGroupEntity(request, optionalQuizGroup);
        quizGroup.ifPresent(this::saveQuizGroup);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> saveQuestion(CreateOrUpdateQuestion request) {
        cacheProviderManager.evictQuestionRelatedCaches();
        Optional<Question> optionalQuestion = request.getId() != null ? questionRepository.findById(request.getId()) : Optional.empty();
        Optional<Question> question = questionMapper.toQuestionEntity(request, optionalQuestion);
        if (question.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        if (!validateAnswers(question)) {
            return ResponseEntity.badRequest().build();
        }
        CreateOrUpdateAnswer correctAnswer = request.getCreateOrUpdateAnswerList().stream().filter(CreateOrUpdateAnswer::isCorrectAnswer).findFirst().orElse(null); //todo clienttan update durumunda correct answer gelmiyo kontrol et
        Question savedQuestion = saveQuestionWithAnswers(question.get(), request.getQuizId(), correctAnswer);

        return ResponseEntity.ok().build();
    }

    private boolean validateAnswers(Optional<Question> question) {
        List<Answer> answers = question.get().getAnswersList();

        Set<String> contents = new HashSet<>();

        for (Answer answer : answers) {
            if (!contents.add(answer.getContent())) {
                return false;
            }
        }
        return true;
    }

    public ResponseEntity<List<Quiz>> getQuizListWithGroupIfExist(QuizListWithGroupIdRequest request) {
        return ResponseEntity.ok(getQuizListWithGroupIdIfExist(request));
    }

    public ResponseEntity<List<QuizGroup>> getQuizGroupList(@RequestBody PageRequest request) {
        List<QuizGroup> quizGroupList = getQuizGroups(request);
        return ResponseEntity.ok(quizGroupList);
    }

    public ResponseEntity<List<Question>> getQuestionList(Long quizId) {
        Optional<Quiz> optQuiz = quizRepository.findById(quizId);
        return optQuiz.map(quiz -> ResponseEntity.ok(quiz.getQuestionList())).orElseGet(() -> ResponseEntity.ok(Collections.emptyList()));
    }

    public List<Quiz> getQuizListWithGroupIdIfExist(QuizListWithGroupIdRequest request) {
        try {
            if (request.getQuizGroupId() != null) {
                QuizGroup quizGroup = new QuizGroup();
                quizGroup.setId(request.getQuizGroupId());
                return quizRepository.findAllByQuizGroupListContainingOrderByPriorityAsc(quizGroup, org.springframework.data.domain.PageRequest.of(request.getPage(), request.getPageSize()));
            }
            return quizRepository.findAllByAppId(getAppId(),org.springframework.data.domain.PageRequest.of(request.getPage(), request.getPageSize())); //todo bakılacak
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
            return quizGroupRepository.findAllByAppId(getAppId(), org.springframework.data.domain.PageRequest.of(request.getPage(), request.getPageSize()));
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


    public Question saveQuestionWithAnswers(Question question, Long quizId, CreateOrUpdateAnswer correctAnswer) {
        Question savedQuestion = questionRepository.save(question);
        Question savedQuestionWithAnswer = saveQuestionWithAnswer(correctAnswer, savedQuestion);
        addToQuiz(quizId, savedQuestion);
        return savedQuestionWithAnswer;
    }

    private Question saveQuestionWithAnswer(CreateOrUpdateAnswer correctAnswer, Question savedQuestion) {
        if (correctAnswer != null) {
            Long correctId = savedQuestion.getAnswersList()
                    .stream()
                    .filter(answer -> answer.getContent().equalsIgnoreCase(correctAnswer.getContent()))
                    .map(Answer::getId)
                    .findFirst()
                    .orElse(null);
            savedQuestion.setCorrectAnswerId(correctId);
            return questionRepository.save(savedQuestion);
        }
        return savedQuestion;
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

    public ResponseEntity<List<UserResponse>> getFilteredUsers(UserFilterRequest request) {
        return ResponseEntity.ok(userManagementManager.getFilteredUsers(request));
    }

    public ResponseEntity<Boolean> fillQuizWithQuestions(FillQuizRequest request) {
        List<Quiz> allQuizzes = quizRepository.findAllByAppId(getAppId(),org.springframework.data.domain.PageRequest.of(0, 100));
        List<Question> questionList = allQuizzes
                .stream()
                .filter(Quiz::isActive)
                .filter(quiz -> !StringUtils.hasText(request.getDifficulty()) || quiz.getAttributes().containsValue(request.getDifficulty()))
                .flatMap(quiz -> quiz.getQuestionList().stream())
                .collect(Collectors.toList());

        List<Question> mixedQuestions = allQuizzes.stream().filter(quiz -> quiz.getName().toLowerCase(Locale.ROOT).contains("mixed")).flatMap(quiz -> quiz.getQuestionList().stream())
                .toList();

        questionList.removeAll(mixedQuestions);

        List<Question> randomQuestions = questionList.stream().collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
            Collections.shuffle(list);
            return list.stream().limit(request.getQuizSize()).toList();
        }));

        Optional<Quiz> quizToBeFilled = quizRepository.findById(request.getQuizId());
        Quiz quiz = quizToBeFilled.orElseThrow(RuntimeException::new);
        quiz.setQuestionList(new ArrayList<>(randomQuestions));
        quiz.setActiveQuestionCount(randomQuestions.size());
        quizRepository.save(quiz);
        return ResponseEntity.ok(true);
    }
}
