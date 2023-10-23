package com.example.product.model.function;

import com.example.product.entity.Product;
import com.example.product.model.ProductsModel;

import java.util.List;
import java.util.function.Function;

public class ProductsToModelFunction implements Function<List<Product>, ProductsModel> {
    @Override
    public ProductsModel apply(List<Product> entity) {
        return ProductsModel.builder()
                .products(entity.stream()
                        .map(product -> ProductsModel.Product.builder()
                                .id(product.getId())
                                .name(product.getName())
                                .build())
                        .toList())
                .build();
    }
}
