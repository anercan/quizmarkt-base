package com.quizmarkt.base.data.response;

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
public class QuizGroupResponse extends ServiceResponse {

    List<QuizGroupWithUserData> quizGroupWithUserDataList;

}
