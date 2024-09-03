package com.quizmarkt.base.data.request;

import lombok.Data;

import java.util.List;

/**
 * @author anercan
 */

@Data
public class CreateOrUpdateQuiz {
    private Long id;
    private String name;
    private String attributes;
    private int priority;
    private boolean active;
    private List<Long> quizGroupIds;
}
