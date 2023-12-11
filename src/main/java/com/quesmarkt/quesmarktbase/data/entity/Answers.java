package com.quesmarkt.quesmarktbase.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author anercan
 */

@Getter
@Setter
@Entity
@Table(name = "answers")
public class Answers extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String imgUrl;

    @Override
    public Long getId() {
        return id;
    }
}
