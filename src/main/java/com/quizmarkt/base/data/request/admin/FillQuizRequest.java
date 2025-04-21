package com.quizmarkt.base.data.request.admin;

import lombok.Data;

import java.util.List;

/**
 * @author anercan
 */

@Data
public class FillQuizRequest {
    private int appId;
    private Long quizId;
    private List<String> subjects;
    private String difficulty;
    private int quizSize;
}
