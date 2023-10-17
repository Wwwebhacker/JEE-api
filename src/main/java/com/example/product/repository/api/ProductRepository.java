package com.example.product.repository.api;

import com.example.product.entity.Product;
import com.example.repository.api.Repository;

import java.util.UUID;

public interface ProductRepository extends Repository<Product, UUID> {
}
