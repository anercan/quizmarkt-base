package com.quizmarkt.base.manager;

import com.quizmarkt.base.data.entity.Answer;
import com.quizmarkt.base.data.entity.Question;
import com.quizmarkt.base.data.entity.Quiz;
import com.quizmarkt.base.data.repository.QuestionRepository;
import com.quizmarkt.base.data.repository.QuizRepository;
import com.quizmarkt.base.data.request.CreateOrUpdateAnswer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author anercan
 */
@AllArgsConstructor
@Service
public class QuestionManager extends BaseManager {

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;

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
