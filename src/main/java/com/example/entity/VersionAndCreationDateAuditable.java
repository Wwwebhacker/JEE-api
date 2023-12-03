package com.example.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * Base super class providing support for optimistic locking version and creation date.
 */
@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class VersionAndCreationDateAuditable {

    /**
     * Edit version fore optimistic locking.
     */
    @Version
    private Long version;

    /**
     * Creation date.
     */
    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;

    /**
     * Update date.
     */
    @Column(name = "update_date_time")
    private LocalDateTime updateDateTime;

    /**
     * Update creation datetime.
     */
    @PrePersist
    public void updateCreationDateTime() {
        creationDateTime = LocalDateTime.now();
    }

    @PreUpdate
    public void updateUpdateDateTime() {
        updateDateTime = LocalDateTime.now();
    }

}
