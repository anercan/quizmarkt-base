package com.quizmarkt.base.data.request;

import lombok.Data;

/**
 * @author anercan
 */

@Data
public class AddOrRemoveFavoriteRequest {
    private boolean isAdd;
    private Long questionId;
}
