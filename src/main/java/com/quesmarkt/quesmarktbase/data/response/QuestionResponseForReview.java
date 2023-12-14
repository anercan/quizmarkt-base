package com.quesmarkt.quesmarktbase.data.response;

import lombok.Data;

/**
 * @author anercan
 */

@Data
public class QuestionResponseForReview {
    private Long id;
    private String content;
    private String imgUrl;
    private Long correctAnswerId;
    private String explanation;
    private int priority;
}
