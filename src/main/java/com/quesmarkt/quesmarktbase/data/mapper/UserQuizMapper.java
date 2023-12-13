package com.quesmarkt.quesmarktbase.data.mapper;

import com.quesmarkt.quesmarktbase.data.entity.UserQuiz;
import com.quesmarkt.quesmarktbase.data.response.UserQuizResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author anercan
 */

@Mapper(componentModel = "spring")
public interface UserQuizMapper {

    @Mapping(target = "wrongQuestionCount", expression = "java(userQuiz.getWrongQuestionList().size())")
    UserQuizResponse toUserQuizResponse(UserQuiz userQuiz);
}
