package com.example.datastore.component;

import com.example.borrowedItem.entity.BorrowedItem;
import com.example.product.entity.Product;
import com.example.serialization.component.CloningUtility;
import com.example.user.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class DataStore {
    private final Set<User> users = new HashSet<>();
    private final Set<Product> products = new HashSet<>();

    private final Set<BorrowedItem> borrowedItems  = new HashSet<>();

    private final CloningUtility cloningUtility;


    @Inject
    public DataStore(CloningUtility cloningUtility) {
        this.cloningUtility = cloningUtility;
    }

    public synchronized List<User> findAllUsers() {
        return users.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createUser(User value) throws IllegalArgumentException {
        if (users.stream().anyMatch(user -> user.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The user id \"%s\" is not unique".formatted(value.getId()));
        }
        users.add(cloningUtility.clone(value));
    }

    public synchronized List<Product> findAllProducts() {
        return products.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createProduct(Product value) throws IllegalArgumentException {
        if (products.stream().anyMatch(product -> product.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The product id \"%s\" is not unique".formatted(value.getId()));
        }
        products.add(cloningUtility.clone(value));
    }

    public synchronized void updateProduct(Product value) throws IllegalArgumentException {
        Product entity = cloningUtility.clone(value);
        if (products.removeIf(product -> product.getId().equals(value.getId()))) {
            products.add(entity);
        }else {
            throw new IllegalArgumentException("The product with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public synchronized void updateItem(BorrowedItem value) throws IllegalArgumentException {
        BorrowedItem entity = cloneWithRelations(value);
        if (borrowedItems.removeIf(borrowedItem -> borrowedItem.getId().equals(value.getId()))) {
            borrowedItems.add(entity);
        }else {
            throw new IllegalArgumentException("The borrowedItem with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public synchronized List<BorrowedItem> findAllBorrowedItems(){
        return borrowedItems.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createBorrowedItem(BorrowedItem value) throws IllegalArgumentException {
        if (borrowedItems.stream().anyMatch(bI -> bI.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The borrowed Item id \"%s\" is not unique".formatted(value.getId()));
        }

        BorrowedItem entity = cloneWithRelations(value);
        borrowedItems.add(entity);
    }

    private BorrowedItem cloneWithRelations(BorrowedItem value) {
        BorrowedItem entity = cloningUtility.clone(value);

        if (entity.getUser() != null) {
            entity.setUser(users.stream()
                    .filter(user -> user.getId().equals(value.getUser().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No user with id \"%s\".".formatted(value.getUser().getId()))));
        }

        if (entity.getProduct() != null) {
            entity.setProduct(products.stream()
                    .filter(product -> product.getId().equals(value.getProduct().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No product with id \"%s\".".formatted(value.getProduct().getId()))));
        }

        return entity;
    }



    public synchronized void deleteBorrowedItem(UUID id) throws IllegalArgumentException {
        if (!borrowedItems.removeIf(borrowedItem -> borrowedItem.getId().equals(id))) {
            throw new IllegalArgumentException("The borrowedItem with id \"%s\" does not exist".formatted(id));
        }
    }

    public synchronized void deleteProduct(UUID id) throws IllegalArgumentException {
        Product product = findAllProducts().stream().filter(p -> p.getId().equals(id)).findFirst().orElseThrow(() -> new IllegalArgumentException("The product with id \"%s\" does not exist".formatted(id)));

        List<BorrowedItem> borrowedItemsToDelete = findAllBorrowedItems().stream().filter(borrowedItem -> borrowedItem.getProduct().equals(product)).toList();

        borrowedItemsToDelete.forEach(borrowedItem -> deleteBorrowedItem(borrowedItem.getId()));

        products.remove(product);
    }

}
