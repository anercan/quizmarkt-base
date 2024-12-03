package com.quizmarkt.base.data.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author anercan
 */

@Data
@Builder
public class UserDataResponse {
    private long userSolvedQuizCount;
    private long userOngoingQuizCount;
    private int totalQuizCount;
    private String avatarUrl;
    private Map<String,Integer> wrongsMap;
    private List<ActivityData> activityDataList;
}
