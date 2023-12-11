package com.quesmarkt.quesmarktbase.data.entity.listener;

import com.quesmarkt.quesmarktbase.data.entity.BaseEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class AuditingEntityListener {
    @PrePersist
    @PreUpdate
    public void setLastModifiedDate(@SuppressWarnings("rawtypes") BaseEntity entity) {
        if (entity.getId() == null) {
            entity.setCreatedDate(ZonedDateTime.now());
        }
        entity.setLastModifiedDate(ZonedDateTime.now());
        if (entity.getCreatedBy() == null) {
            entity.setCreatedBy("SYSTEM");
        }
    }
}
