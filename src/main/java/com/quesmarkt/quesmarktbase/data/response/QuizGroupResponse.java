package com.quesmarkt.quesmarktbase.data.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author anercan
 */

@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
public class QuizGroupResponse extends ServiceResponse {

    List<QuizGroupWithUserData> quizGroupWithUserDataList = new ArrayList<>();

}
