package com.quizmarkt.base.data.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quizmarkt.base.data.entity.QuizGroup;
import com.quizmarkt.base.data.request.CreateOrUpdateQuizGroup;
import com.quizmarkt.base.data.response.QuizGroupWithUserData;
import org.apache.commons.lang3.StringUtils;
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
            quizGroup.setActive(request.isActive());
            quizGroup.setAppId(1);
            quizGroup.setDescription(request.getDescription());
            quizGroup.setTitle(request.getTitle());
            quizGroup.setPriority(request.getPriority());
            if (StringUtils.isNotEmpty(request.getAttributes())) {
                quizGroup.setAttributes(new ObjectMapper().readValue(request.getAttributes(), Map.class));
            }
            return Optional.of(quizGroup);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
