package com.quizmarkt.base.data.request.admin;

import lombok.Data;

import java.util.List;

/**
 * @author anercan
 */

@Data
public class CreateOrUpdateQuestion {

    private Long id;
    private String content;
    private String attributes;
    private int priority;
    private boolean active;
    private Long quizId;
    private String imgUrl;
    private String correctAnswerId;
    private String explanation;

    private List<CreateOrUpdateAnswer> createOrUpdateAnswerList;
}
