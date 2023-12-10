package com.quesmarkt.quesmarktbase.data.entity;

import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author anercan
 */

@Getter
@Setter
@Table(name = "answers")
public class Answers extends BaseEntity<Long> {
    private Long id;
    private String content;
    private String imgUrl;

    @Override
    public Long getIdentifier() {
        return id;
    }
}
