package com.quizmarkt.base.manager;

import com.quizmarkt.base.data.entity.Question;
import com.quizmarkt.base.data.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author anercan
 */
@AllArgsConstructor
@Service
public class QuestionManager extends BaseManager {

    private final QuestionRepository questionRepository;

    public List<Question> getRandomQuestionListWithAppId(Integer appId, int quizSize) {
        try {
            return questionRepository.findRandomQuestionsWithAppId(appId, PageRequest.of(0, quizSize));
        } catch (Exception e) {
            logger.error("getRandomQuestionListWithAppId got exception appId:{}", appId, e);
            return Collections.emptyList();
        }
    }

    public List<Question> getQuestionsWithIdList(Collection<Long> questionIds) { //todo belki attribute için kullanıldığı yerde sadece attribute çekilerek yönetilebilir
        return questionRepository.findAllById(questionIds);
    }

    public List<Question> getQuestionsWithIdListWithOrder(List<Long> questionIds) {
        List<Question> favoriteQuestions = questionRepository.findAllById(questionIds);

        Map<Long, Question> questionMap = favoriteQuestions.stream()
                .collect(Collectors.toMap(Question::getId, Function.identity()));

        List<Question> orderedList = questionIds.stream()
                .map(questionMap::get)
                .collect(Collectors.toList());
        Collections.reverse(orderedList);
        return orderedList;
    }
}
