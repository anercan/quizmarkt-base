package com.quesmarkt.quesmarktbase.data.mapper;

import com.quesmarkt.quesmarktbase.data.entity.QuizGroup;
import com.quesmarkt.quesmarktbase.data.response.QuizGroupWithUserData;
import org.mapstruct.Mapper;

import java.util.Map;
import java.util.Objects;

/**
 * @author anercan
 */

@Mapper(componentModel = "spring")
public interface QuizGroupMapper {
    QuizGroupWithUserData toQuizGroupResponse(QuizGroup entity);

    default QuizGroupWithUserData getQuizGroupWithUserData(Map<Long, Integer> userQuizMap, QuizGroup quizGroup) {
        QuizGroupWithUserData quizGroupWithUserData = this.toQuizGroupResponse(quizGroup);
        Integer userSolvedCount = userQuizMap.get(quizGroup.getId());
        quizGroupWithUserData.setUserSolvedCount(Objects.isNull(userSolvedCount) ? 0 : userSolvedCount);
        return quizGroupWithUserData;
    }
}
