package com.example.borrowedItem.repository.memory;

import com.example.borrowedItem.entity.BorrowedItem;
import com.example.borrowedItem.repository.api.BorrowedItemRepository;
import com.example.datastore.component.DataStore;
import com.example.product.entity.Product;
import com.example.user.entity.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestScoped
public class BorrowedItemInMemoryRepository implements BorrowedItemRepository {


    private final DataStore store;

    @Inject
    public BorrowedItemInMemoryRepository(DataStore store) { this.store = store; }
    @Override
    public Optional<BorrowedItem> findByIdAndUser(UUID id, User user) {
        return store.findAllBorrowedItems().stream()
                .filter(borrowedItem -> borrowedItem.getUser().equals(user))
                .filter(borrowedItem -> borrowedItem.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<BorrowedItem> findAllByUser(User user) {
        return store.findAllBorrowedItems().stream()
                .filter(borrowedItem -> user.equals(borrowedItem.getUser()))
                .collect(Collectors.toList());
    }

    @Override
    public List<BorrowedItem> findAllByProduct(Product product) {
        return store.findAllBorrowedItems().stream()
                .filter(borrowedItem -> product.equals(borrowedItem.getProduct()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BorrowedItem> find(UUID id) {
        return store.findAllBorrowedItems().stream()
                .filter(borrowedItem -> borrowedItem.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<BorrowedItem> findAll() {
        return store.findAllBorrowedItems();
    }

    @Override
    public void create(BorrowedItem entity) {
        store.createBorrowedItem(entity);
    }

    @Override
    public void delete(BorrowedItem entity) {
        store.deleteBorrowedItem(entity.getId());
    }

    @Override
    public void update(BorrowedItem entity) {
        throw new UnsupportedOperationException("Not implemented.");
    }
}
