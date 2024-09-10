package com.quizmarkt.base.data.response;

import lombok.Builder;
import lombok.Data;

/**
 * @author anercan
 */

@Data
@Builder
public class UserDataResponse {
    private int userSolvedQuizCount;
    private int userOngoingQuizCount;
    private int totalQuizCount;
    private String avatarUrl;
}
