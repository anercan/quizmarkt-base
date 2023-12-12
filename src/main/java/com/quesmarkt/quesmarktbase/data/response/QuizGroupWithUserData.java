package com.quesmarkt.quesmarktbase.data.response;

import lombok.Data;

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
}
