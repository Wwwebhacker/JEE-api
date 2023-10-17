package com.example.borrowedItem.repository.api;

import com.example.borrowedItem.entity.BorrowedItem;
import com.example.product.entity.Product;
import com.example.repository.api.Repository;
import com.example.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BorrowedItemRepository extends Repository<BorrowedItem, UUID> {

    Optional<BorrowedItem> findByIdAndUser(UUID id, User user);

    List<BorrowedItem> findAllByUser(User user);

    List<BorrowedItem> findAllByProduct(Product product);

}
