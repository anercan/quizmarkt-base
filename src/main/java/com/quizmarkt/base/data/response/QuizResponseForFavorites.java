package com.quizmarkt.base.data.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author anercan
 */

@Builder
@Data
public class QuizResponseForFavorites {
    private String name;
    private List<QuestionResponse> questionList;
}
