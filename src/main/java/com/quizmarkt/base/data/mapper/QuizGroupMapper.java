package com.quizmarkt.base.data.mapper;

import com.quizmarkt.base.data.entity.QuizGroup;
import com.quizmarkt.base.data.request.admin.CreateOrUpdateQuizGroup;
import com.quizmarkt.base.data.response.QuizGroupWithUserData;
import com.quizmarkt.base.util.MapperUtils;
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
            quizGroup.setAppId(request.getAppId());
            quizGroup.setDescription(request.getDescription());
            quizGroup.setTitle(request.getTitle());
            quizGroup.setPriority(request.getPriority());
            if (StringUtils.isNotEmpty(request.getAttributes())) {
                Map<String, String> attributeMap = MapperUtils.getAttributeMapFromString(request.getAttributes());
                if (attributeMap != null) {
                    quizGroup.setAttributes(attributeMap);
                }
            }
            return Optional.of(quizGroup);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
