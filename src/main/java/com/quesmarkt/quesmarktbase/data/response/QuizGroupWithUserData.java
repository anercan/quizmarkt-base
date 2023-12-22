package com.quesmarkt.quesmarktbase.data.response;

import lombok.Data;

import java.util.Map;

/**
 * @author anercan
 */

@Data
public class QuizGroupWithUserData {

    private Long id;
    private String title;
    private String description;
    private String color;
    private String imageUrl;
    private int quizQuantity;
    private int userSolvedCount;
    private Map<String,String> attributes;

}
