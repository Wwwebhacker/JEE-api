package com.example.product.dto;

import com.example.product.entity.Product;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetProductResponse {

    private UUID id;

    private String name;

    private String brand;
}
