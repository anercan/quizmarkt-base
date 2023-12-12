package com.quesmarkt.quesmarktbase.data.request;

import lombok.Data;

/**
 * @author anercan
 */

@Data
public class QuizListWithUserDataRequest {
    int pageSize;
    int page;
    Long quizGroupId;
}
