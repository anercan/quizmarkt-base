package com.quesmarkt.quesmarktbase.data.response;

import com.quesmarkt.quesmarktbase.data.enums.UserQuizState;
import lombok.Data;

import java.time.ZonedDateTime;

/**
 * @author anercan
 */

@Data
public class UserQuizInListResponse {
    private Long id;
    private Long userId;
    private int timeTaken;
    private UserQuizState state;
    private ZonedDateTime completeDate;
    private Long quizGroupId;
    private int solvedCount;
}
