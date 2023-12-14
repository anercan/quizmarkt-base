package com.quesmarkt.quesmarktbase.data.response;

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
public class ReviewWrongQuestionListResponse extends ServiceResponse {
    List<UserWrongAnswerResponse> reviewWrongQuestionList;
}
