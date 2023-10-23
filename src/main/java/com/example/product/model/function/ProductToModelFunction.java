package com.example.product.model.function;

import com.example.product.entity.Product;
import com.example.product.model.ProductModel;

import java.util.function.Function;

public class ProductToModelFunction implements Function<Product, ProductModel> {
    @Override
    public ProductModel apply(Product entity) {
        return ProductModel.builder()
                .brand(entity.getBrand())
                .name(entity.getName())
                .build();
    }
}
