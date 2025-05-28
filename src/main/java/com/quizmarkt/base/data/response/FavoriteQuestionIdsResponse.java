package com.quizmarkt.base.data.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author anercan
 */

@Data
@Builder
public class FavoriteQuestionIdsResponse {
    private List<Long> favoriteIds;
}
