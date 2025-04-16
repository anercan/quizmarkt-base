package com.quizmarkt.base.data.mapper;

import com.quizmarkt.base.data.entity.UserDailyQuiz;
import com.quizmarkt.base.data.response.UserDailyQuizResponse;
import org.mapstruct.Mapper;

/**
 * @author anercan
 */

@Mapper(componentModel = "spring")
public interface UserDailyQuizMapper {

    UserDailyQuizResponse toResponse(UserDailyQuiz entity);
}
