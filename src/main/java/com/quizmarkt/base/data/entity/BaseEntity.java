package com.quizmarkt.base.data.entity;

import com.quizmarkt.base.data.entity.listener.AuditingEntityListener;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

/**
 * @author anercan
 */

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EntityListeners({AuditingEntityListener.class})
@Getter
@Setter
public abstract class BaseEntity<T> {
    private String createdBy;
    private ZonedDateTime createdDate;
    private ZonedDateTime lastModifiedDate;
    public abstract T getId();
}
