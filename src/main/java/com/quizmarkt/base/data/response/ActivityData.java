package com.quizmarkt.base.data.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author anercan
 */

@Data
@Builder
@AllArgsConstructor
public class ActivityData {
    private String date;
    private int count;
}
