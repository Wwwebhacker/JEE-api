package com.example.product.dto.function;

import com.example.product.dto.PutProductRequest;
import com.example.product.entity.Product;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToProductFunction implements BiFunction<UUID, PutProductRequest, Product> {
    @Override
    public Product apply(UUID uuid, PutProductRequest request) {
        return Product.builder()
                .id(uuid)
                .name(request.getName())
                .brand(request.getBrand())
                .build();
    }
}
