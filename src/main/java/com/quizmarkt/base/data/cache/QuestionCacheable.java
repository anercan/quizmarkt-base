package com.quizmarkt.base.data.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.Map;

/**
 * @author anercan
 */

@AllArgsConstructor
@Getter
public class QuestionCacheable {
    private final Long id;
    private final String createdBy;
    private final ZonedDateTime createdDate;
    private final ZonedDateTime lastModifiedDate;
    private final String content;
    private final String imgUrl;
    private final Long correctAnswerId;
    private final boolean active;
    private final String explanation;
    private final int priority;
    private final Map<String, String> attributes;
}
