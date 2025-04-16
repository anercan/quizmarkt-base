package com.quizmarkt.base.manager;

import com.quizmarkt.base.data.entity.Question;
import com.quizmarkt.base.data.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

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

    public List<Question> getQuestionsWithIdList(List<Long> questionIds) { //todo belki attribute için kullanıldığı yerde sadece attribute çekilerek yönetilebilir
        return questionRepository.findAllById(questionIds);
    }
}
