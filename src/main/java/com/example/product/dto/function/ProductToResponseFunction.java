package com.example.product.dto.function;

import com.example.product.dto.GetProductResponse;
import com.example.product.entity.Product;

import java.util.function.Function;

public class ProductToResponseFunction implements Function<Product, GetProductResponse> {
    @Override
    public GetProductResponse apply(Product product) {
        return GetProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .brand(product.getBrand())
                .build();
    }
}
