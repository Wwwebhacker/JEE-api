package com.example.borrowedItem.entity;


import com.example.entity.VersionAndCreationDateAuditable;
import com.example.product.entity.Product;
import com.example.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
@EqualsAndHashCode()
@Entity
@Table(name = "items")
public class BorrowedItem extends VersionAndCreationDateAuditable implements Serializable {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "app_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product")
    private Product product;


    private LocalDate date;

    @PrePersist
    @Override
    public void updateCreationDateTime() {
        super.updateCreationDateTime();
    }

    @PreUpdate
    @Override
    public void updateUpdateDateTime() {
        super.updateUpdateDateTime();
    }
}
