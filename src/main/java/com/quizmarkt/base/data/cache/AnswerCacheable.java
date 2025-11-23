package com.quizmarkt.base.data.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

/**
 * @author anercan
 */


@Getter
@AllArgsConstructor
public class AnswerCacheable {
    private final Long id;
    private final String createdBy;
    private final ZonedDateTime createdDate;
    private final ZonedDateTime lastModifiedDate;
    private final String content;
    private final String imgUrl;
}
