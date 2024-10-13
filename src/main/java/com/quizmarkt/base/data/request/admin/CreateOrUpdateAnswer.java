package com.quizmarkt.base.data.request.admin;

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
