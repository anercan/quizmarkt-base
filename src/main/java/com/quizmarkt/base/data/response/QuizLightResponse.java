package com.quizmarkt.base.data.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Map;

/**
 * @author anercan
 */

@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@Data
public class QuizLightResponse {
    private Long id;
    private String name;
    private Map<String, String> attributes;
    private int activeQuestionCount;
}
