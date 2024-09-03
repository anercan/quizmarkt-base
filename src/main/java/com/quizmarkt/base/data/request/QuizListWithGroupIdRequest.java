package com.quizmarkt.base.data.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author anercan
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class QuizListWithGroupIdRequest extends PageRequest {
    private Long quizGroupId;
}
