package com.quizmarkt.base.data.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author anercan
 */

@Getter
@Setter
@SuperBuilder
public class UserQuizListResponse {
    List<SolvedQuizListResponse> userQuizResponseList;
}
