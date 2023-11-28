package com.example.product.service;


import com.example.product.entity.Product;
import com.example.product.repository.api.ProductRepository;
import com.example.user.entity.UserRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
@Log
public class ProductService {

    private final ProductRepository repository;

    @Inject
    public ProductService(ProductRepository repository) { this.repository = repository; }

    public Optional<Product> find(UUID uuid)  { return repository.find(uuid); }

    public List<Product> findAll() { return repository.findAll(); }


    public void create(Product product) {
        if (repository.find(product.getId()).isPresent()){
            throw new IllegalArgumentException("Product already exists.");
        }
        repository.create(product);
    }

    public void delete(UUID id) {
        repository.delete(repository.find(id).orElseThrow());
    }

    public void update(Product product) {
        repository.update(product);
    }

}
