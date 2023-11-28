package com.example.borrowedItem.repository.persistance;

import com.example.borrowedItem.entity.BorrowedItem;
import com.example.borrowedItem.repository.api.BorrowedItemRepository;
import com.example.product.entity.Product;
import com.example.user.entity.User;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class ItemPersistenceRepository implements BorrowedItemRepository {


    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }



    @Override
    public Optional<BorrowedItem> find(UUID id) {
        return Optional.ofNullable(em.find(BorrowedItem.class, id));
    }

    @Override
    public List<BorrowedItem> findAll() {
        return em.createQuery("select i from BorrowedItem i", BorrowedItem.class).getResultList();
    }

    @Transactional
    @Override
    public void create(BorrowedItem entity) {
        em.persist(entity);
    }

    @Transactional
    @Override
    public void delete(BorrowedItem entity) {
        em.remove(em.find(BorrowedItem.class, entity.getId()));
    }

    @Transactional
    @Override
    public void update(BorrowedItem entity) {
        em.merge(entity);
    }

    @Override
    public Optional<BorrowedItem> findByIdAndUser(UUID id, User user) {
        try {
            return Optional.of(em.createQuery("select i from BorrowedItem i where i.id = :id and i.user = :user", BorrowedItem.class)
                    .setParameter("user", user)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<BorrowedItem> findAllByUser(User user) {
        return em.createQuery("select i from BorrowedItem i where i.user = :user", BorrowedItem.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public List<BorrowedItem> findAllByProduct(Product product) {
        return em.createQuery("select i from BorrowedItem i where i.product = :product", BorrowedItem.class)
                .setParameter("product", product)
                .getResultList();
    }
}
