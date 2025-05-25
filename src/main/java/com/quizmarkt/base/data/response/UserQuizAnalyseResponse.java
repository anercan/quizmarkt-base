package com.quizmarkt.base.data.response;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * @author anercan
 */

@Data
@Builder
public class UserQuizAnalyseResponse {
    private Map<String,Integer> wrongsMap;
}
