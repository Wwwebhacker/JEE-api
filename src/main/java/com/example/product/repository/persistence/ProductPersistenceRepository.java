package com.example.product.repository.persistence;

import com.example.product.entity.Product;
import com.example.product.repository.api.ProductRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class ProductPersistenceRepository implements ProductRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Product> find(UUID id) {
        return Optional.ofNullable(em.find(Product.class, id));
    }

    @Override
    public List<Product> findAll() {
        return em.createQuery("select p from Product p", Product.class).getResultList();
    }

    @Transactional
    @Override
    public void create(Product entity) {
        em.persist(entity);
    }

    @Transactional
    @Override
    public void delete(Product entity) {
        em.remove(em.find(Product.class, entity.getId()));
    }

    @Transactional
    @Override
    public void update(Product entity) {
        em.merge(entity);
    }
}
