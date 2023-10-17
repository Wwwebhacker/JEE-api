package com.example.borrowedItem.entity;


import com.example.product.entity.Product;
import com.example.user.entity.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class BorrowedItem implements Serializable {
    private UUID id;

    private User user;
    private Product product;

}
