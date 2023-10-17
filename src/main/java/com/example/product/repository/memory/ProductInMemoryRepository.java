package com.example.product.repository.memory;

import com.example.datastore.component.DataStore;
import com.example.product.entity.Product;
import com.example.product.repository.api.ProductRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class ProductInMemoryRepository implements ProductRepository {

    private final DataStore store;

    @Inject
    public ProductInMemoryRepository(DataStore store){ this.store = store;}

    @Override
    public Optional<Product> find(UUID id) {
        return store.findAllProducts().stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Product> findAll() {
        return store.findAllProducts();
    }

    @Override
    public void create(Product entity) {
        store.createProduct(entity);
    }

    @Override
    public void delete(Product entity) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void update(Product entity) {
        throw new UnsupportedOperationException("Not implemented.");
    }
}
