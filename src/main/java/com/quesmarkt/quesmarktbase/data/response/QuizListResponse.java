package com.quesmarkt.quesmarktbase.data.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author anercan
 */

@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
public class QuizListResponse extends ServiceResponse {

    List<QuizResponseWithUserData> quizResponseWithUserDataList;

}
