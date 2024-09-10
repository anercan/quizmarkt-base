package com.quizmarkt.base.data.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quizmarkt.base.data.entity.Quiz;
import com.quizmarkt.base.data.entity.QuizGroup;
import com.quizmarkt.base.data.entity.UserQuiz;
import com.quizmarkt.base.data.request.CreateOrUpdateQuiz;
import com.quizmarkt.base.data.response.QuizResponse;
import com.quizmarkt.base.data.response.QuizResponseWithUserData;
import com.quizmarkt.base.util.UserQuizUtil;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author anercan
 */

@Mapper(componentModel = "spring")
public interface QuizMapper {

    @Mapping(source = "activeQuestionCount", target = "questionCount")
    QuizResponseWithUserData toQuizResponseWithUserData(Quiz quiz);

    default QuizResponseWithUserData getQuizResponseWithUserData(Map<Long, UserQuiz> quizIdUserQuizMap, Quiz quiz) {
        QuizResponseWithUserData quizGroupWithUserData = this.toQuizResponseWithUserData(quiz);
        UserQuiz userQuiz = quizIdUserQuizMap.get(quiz.getId());
        if (userQuiz != null) {
            quizGroupWithUserData.setSolvedCount(UserQuizUtil.getSolvedQuestionDataOfUserQuiz(userQuiz));
            quizGroupWithUserData.setState(userQuiz.getState());
        } else {
            quizGroupWithUserData.setSolvedCount(0);
        }
        return quizGroupWithUserData;
    }

    QuizResponse toQuizResponse(Quiz quiz);

    default Optional<Quiz> toQuizEntity(CreateOrUpdateQuiz request, Optional<Quiz> optionalQuiz) {
        try {
            Quiz quiz = optionalQuiz.orElseGet(Quiz::new);
            quiz.setActive(request.isActive());
            quiz.setName(request.getName());
            quiz.setAppId(1);
            quiz.setPriority(request.getPriority());
            if (StringUtils.isNotEmpty(request.getAttributes())) {
                quiz.setAttributes(new ObjectMapper().readValue(request.getAttributes(), Map.class));
            }
            if (!CollectionUtils.isEmpty(request.getQuizGroupIds())) {
                List<QuizGroup> quizGroupList = new ArrayList<>();
                request.getQuizGroupIds().forEach(id-> quizGroupList.add(new QuizGroup(id)));
                quiz.setQuizGroupList(quizGroupList);
            }
            return Optional.of(quiz);
        } catch (Exception e) {
            return Optional.empty();
        }
    }}
