package com.quesmarkt.quesmarktbase.data.mapper;

import com.quesmarkt.quesmarktbase.data.entity.QuizGroup;
import com.quesmarkt.quesmarktbase.data.response.QuizGroupWithUserData;
import org.mapstruct.Mapper;

import java.util.Map;

/**
 * @author anercan
 */

@Mapper(componentModel = "spring")
public interface QuizGroupMapper {
    QuizGroupWithUserData toQuizGroupResponse(QuizGroup entity);

    default QuizGroupWithUserData getQuizGroupWithUserData(Map<Long, Integer> userQuizMap, QuizGroup quizGroup) {
        QuizGroupWithUserData quizGroupWithUserData = this.toQuizGroupResponse(quizGroup);
        quizGroupWithUserData.setUserSolvedCount(userQuizMap.get(quizGroup.getId()));
        return quizGroupWithUserData;
    }
}
