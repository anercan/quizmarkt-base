package com.quesmarkt.quesmarktbase.data.request;

import lombok.Data;

/**
 * @author anercan
 */

@Data
public class CreateOrUpdateQuizGroup {
    private Long id;
    private String title;
    private String description;
    private String attributes;
    private String color;
    private int priority;
    private boolean active;
}
