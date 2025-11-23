package com.quizmarkt.base.data.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author anercan
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageRequest {
    private int pageSize;
    private int page;
}
