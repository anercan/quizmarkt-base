package com.quesmarkt.quesmarktbase.data.mapper;

import com.quesmarkt.quesmarktbase.data.entity.QuizGroup;
import com.quesmarkt.quesmarktbase.data.response.QuizGroupWithUserData;
import org.mapstruct.Mapper;

/**
 * @author anercan
 */

@Mapper(componentModel = "spring")
public interface QuizGroupMapper {
    QuizGroupWithUserData toQuizGroupResponse(QuizGroup entity);
}
