package com.quesmarkt.quesmarktbase.data.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quesmarkt.quesmarktbase.data.entity.QuizGroup;
import com.quesmarkt.quesmarktbase.data.request.CreateOrUpdateQuizGroup;
import com.quesmarkt.quesmarktbase.data.response.QuizGroupWithUserData;
import org.mapstruct.Mapper;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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

    default Optional<QuizGroup> toQuizGroupEntity(CreateOrUpdateQuizGroup request, Optional<QuizGroup> optionalQuizGroup) {
        try {
            QuizGroup quizGroup = optionalQuizGroup.orElseGet(QuizGroup::new);
            quizGroup.setQuizQuantity(0);
            quizGroup.setActive(request.isActive());
            quizGroup.setAppId(1);
            quizGroup.setDescription(request.getDescription());
            quizGroup.setTitle(request.getTitle());
            quizGroup.setPriority(request.getPriority());
            quizGroup.setAttributes(new ObjectMapper().readValue(request.getAttributes(), Map.class));
            return Optional.of(quizGroup);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
