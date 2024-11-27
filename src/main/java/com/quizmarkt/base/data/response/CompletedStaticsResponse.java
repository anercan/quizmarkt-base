package com.quizmarkt.base.data.response;

import lombok.Builder;
import lombok.Data;

/**
 * @author anercan
 */

@Data
@Builder
public class CompletedStaticsResponse {
    private int worseCount;
    private int betterCount;
    private int equalCount;
}
