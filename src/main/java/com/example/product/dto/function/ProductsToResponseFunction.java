package com.example.product.dto.function;

import com.example.product.dto.GetProductsResponse;
import com.example.product.entity.Product;

import java.util.List;
import java.util.function.Function;

public class ProductsToResponseFunction implements Function<List<Product>, GetProductsResponse> {
    @Override
    public GetProductsResponse apply(List<Product> entities) {
        return GetProductsResponse.builder()
                .products(
                        entities.stream()
                                .map(product -> GetProductsResponse.Product.builder()
                                        .id(product.getId())
                                        .name(product.getName())
                                        .build()).toList())
                .build();
    }
}
