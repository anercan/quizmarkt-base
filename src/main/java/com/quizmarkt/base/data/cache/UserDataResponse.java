package com.quizmarkt.base.data.cache;

import com.quizmarkt.base.data.response.ActivityData;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * @author anercan
 */

@Getter
@Builder
public class UserDataResponse {
    private final long userSolvedQuizCount;
    private final long userOngoingQuizCount;
    private final int totalQuizCount;
    private final String avatarUrl;
    private final Map<String, Integer> wrongsMap;
    private final List<ActivityData> activityDataList;
}
