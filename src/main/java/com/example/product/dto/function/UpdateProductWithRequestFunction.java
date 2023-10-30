package com.example.product.dto.function;

import com.example.product.dto.PatchProductRequest;
import com.example.product.entity.Product;

import java.util.function.BiFunction;

public class UpdateProductWithRequestFunction implements BiFunction<Product, PatchProductRequest, Product> {
    @Override
    public Product apply(Product product, PatchProductRequest patchProductRequest) {
        return Product.builder()
                .id(product.getId())
                .name(patchProductRequest.getName())
                .brand(patchProductRequest.getBrand())
                .build();
    }
}
