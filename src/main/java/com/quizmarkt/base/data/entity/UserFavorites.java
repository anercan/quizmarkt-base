package com.quizmarkt.base.data.entity;

import com.quizmarkt.base.data.converter.LongListConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author anercan
 */

@Getter
@Setter
@Entity
@Table(name = "user_favorites")
public class UserFavorites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private ZonedDateTime lastModifiedDate;

    @Convert(converter = LongListConverter.class)
    private List<Long> questionIds = new ArrayList<>();
}
