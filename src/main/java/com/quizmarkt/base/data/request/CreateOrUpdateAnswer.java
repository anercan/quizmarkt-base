package com.quizmarkt.base.data.request;

import lombok.Data;

/**
 * @author anercan
 */

@Data
public class CreateOrUpdateAnswer {
    Long id;
    String content;
    String imgUrl;
    boolean correctAnswer;
}
