package com.quizmarkt.base.data.response;

import lombok.Data;

import java.util.Map;

/**
 * @author anercan
 */
@Data
public class QuizResponseWithUserData {

    private Long id;
    private String name;
    private int priority;
    private Map<String, String> attributes;
    int solvedCount;
    int questionCount;

}
