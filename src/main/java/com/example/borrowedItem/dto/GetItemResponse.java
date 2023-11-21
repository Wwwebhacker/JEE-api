package com.example.borrowedItem.dto;

import com.example.product.entity.Product;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetItemResponse {
    private UUID id;
    private LocalDate date;
}
