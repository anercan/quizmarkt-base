package com.quizmarkt.base.data.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

/**
 * @author anercan
 */

@Getter
@AllArgsConstructor
public class QuizGroupCacheable {
    private final Long id;
    private final String createdBy;
    private final ZonedDateTime createdDate;
    private final ZonedDateTime lastModifiedDate;
    private final String title;
    private final String description;
    private final String color;
    private final String imageUrl;
    private final boolean active;
    private final int appId;
    private final int priority;
    private final int quizQuantity;
}
