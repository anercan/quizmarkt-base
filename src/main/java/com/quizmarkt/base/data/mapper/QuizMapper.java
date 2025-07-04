package com.quizmarkt.base.data.mapper;

import com.quizmarkt.base.data.entity.Quiz;
import com.quizmarkt.base.data.entity.QuizGroup;
import com.quizmarkt.base.data.entity.UserQuiz;
import com.quizmarkt.base.data.enums.PremiumType;
import com.quizmarkt.base.data.request.admin.CreateOrUpdateQuiz;
import com.quizmarkt.base.data.response.QuizResponse;
import com.quizmarkt.base.data.response.QuizResponseWithUserData;
import com.quizmarkt.base.util.MapperUtils;
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
    QuizResponseWithUserData mapSingleQuiz(Quiz quiz);

    //using mapSingleQuiz
    List<QuizResponseWithUserData> toQuizResponseWithUserData(List<Quiz> quiz);

    default QuizResponseWithUserData getQuizResponseWithUserData(UserQuiz userQuiz, PremiumType userPremiumType,QuizResponseWithUserData quizResponseWithUserData) {
        quizResponseWithUserData.setLocked(!quizResponseWithUserData.getAvailablePremiumTypes().contains(userPremiumType));
        if (userQuiz != null) {
            quizResponseWithUserData.setSolvedCount(UserQuizUtil.getSolvedQuestionDataOfUserQuiz(userQuiz));
            quizResponseWithUserData.setCorrectCount(userQuiz.getCorrectQuestionList().size());
            quizResponseWithUserData.setState(userQuiz.getState());
        } else {
            quizResponseWithUserData.setSolvedCount(0);
        }
        return quizResponseWithUserData;
    }

    QuizResponse toQuizResponse(Quiz quiz);

    default Optional<Quiz> toQuizEntity(CreateOrUpdateQuiz request, Optional<Quiz> optionalQuiz) {
        try {
            Quiz quiz = optionalQuiz.orElseGet(Quiz::new);
            quiz.setActive(request.isActive());
            quiz.setName(request.getName());
            quiz.setAppId(request.getAppId());
            quiz.setPriority(request.getPriority());
            quiz.setAvailablePremiumTypes(request.getAvailablePremiumTypes());
            if (StringUtils.isNotEmpty(request.getAttributes())) {
                Map<String, String> attributeMap = MapperUtils.getAttributeMapFromString(request.getAttributes());
                if (attributeMap != null) {
                    quiz.setAttributes(attributeMap);
                }
            }
            if (!CollectionUtils.isEmpty(request.getQuizGroupIds())) {
                List<QuizGroup> quizGroupList = new ArrayList<>();
                request.getQuizGroupIds().forEach(id -> quizGroupList.add(new QuizGroup(id)));
                quiz.setQuizGroupList(quizGroupList);
            }
            return Optional.of(quiz);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
