package com.quesmarkt.quesmarktbase.data.request;

import lombok.Builder;
import lombok.Data;

/**
 * @author anercan
 */

@Data
@Builder
public class PageRequest {
    int pageSize;
    int page;
}
