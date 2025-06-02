package com.quizmarkt.base.data.request.admin;

import lombok.Data;

/**
 * @author anercan
 */

@Data
public class FillQuizRequest {
    private int appId;
    private Long quizId;
    private String difficulty;
    private int quizSize;
}
