package com.example.borrowedItem.service;


import com.example.borrowedItem.entity.BorrowedItem;
import com.example.borrowedItem.repository.api.BorrowedItemRepository;
import com.example.product.repository.api.ProductRepository;
import com.example.user.entity.User;
import com.example.user.repository.api.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class BorrowedItemService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final BorrowedItemRepository borrowedItemRepository;
    @Inject
    public BorrowedItemService(UserRepository userRepository, BorrowedItemRepository borrowedItemRepository, ProductRepository productRepository){
        this.borrowedItemRepository = borrowedItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public Optional<BorrowedItem> find(UUID id) { return borrowedItemRepository.find(id); }

    public Optional<BorrowedItem> find(User user, UUID id) { return borrowedItemRepository.findByIdAndUser(id, user); }

    public List<BorrowedItem> findAll() { return borrowedItemRepository.findAll(); }

    public List<BorrowedItem> findAll(User user) { return borrowedItemRepository.findAllByUser(user); }

    public void create(BorrowedItem borrowedItem) { borrowedItemRepository.create(borrowedItem); }

    public void delete(UUID id) {
        borrowedItemRepository.delete(borrowedItemRepository.find(id).orElseThrow());
    }


    public Optional<List<BorrowedItem>> findAllByUser(UUID id){
        return userRepository.find(id)
                .map(borrowedItemRepository::findAllByUser);
    }

    public Optional<List<BorrowedItem>> findAllByProduct(UUID id){
        return productRepository.find(id)
                .map(borrowedItemRepository::findAllByProduct);
    }



}
