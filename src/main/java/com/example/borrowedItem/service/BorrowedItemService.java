package com.example.borrowedItem.service;


import com.example.borrowedItem.entity.BorrowedItem;
import com.example.borrowedItem.repository.api.BorrowedItemRepository;
import com.example.product.repository.api.ProductRepository;
import com.example.user.entity.User;
import com.example.user.entity.UserRoles;
import com.example.user.repository.api.UserRepository;
import com.example.user.service.UserService;
import jakarta.ejb.EJB;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.ws.rs.NotAuthorizedException;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class BorrowedItemService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final BorrowedItemRepository borrowedItemRepository;
    private final SecurityContext securityContext;
    private UserService userService;
    @Inject
    public BorrowedItemService(UserRepository userRepository, BorrowedItemRepository borrowedItemRepository, ProductRepository productRepository, SecurityContext securityContext){
        this.borrowedItemRepository = borrowedItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.securityContext = securityContext;
    }

    @EJB
    public void setUserService(UserService userService){ this.userService = userService; }



    public Optional<BorrowedItem> find(UUID id) { return borrowedItemRepository.find(id); }

    public Optional<BorrowedItem> find(User user, UUID id) { return borrowedItemRepository.findByIdAndUser(id, user); }

    public List<BorrowedItem> findAll() {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)){
            return borrowedItemRepository.findAll();
        }

        User user = userService.getLoggedUser().orElseThrow();

        return findAll(user);
    }

    public List<BorrowedItem> findAll(User user) { return borrowedItemRepository.findAllByUser(user); }

    public void create(BorrowedItem borrowedItem) {
        if (borrowedItemRepository.find(borrowedItem.getId()).isPresent()){
            throw new IllegalArgumentException("Item already exists.");
        }
        User user = userService.getLoggedUser().orElseThrow();
        borrowedItem.setUser(user);
        borrowedItemRepository.create(borrowedItem);
    }

    public void delete(UUID id) {
        if(securityContext.isCallerInRole(UserRoles.ADMIN)){
            borrowedItemRepository.delete(borrowedItemRepository.find(id).orElseThrow());
            return;
        }
        User user = userService.getLoggedUser().orElseThrow();

        BorrowedItem borrowedItem = user.getItems().stream().filter(item -> item.getId() == id).findFirst().orElseThrow();
        borrowedItemRepository.delete(borrowedItem);
    }


    public Optional<List<BorrowedItem>> findAllByUser(UUID id){
        return userRepository.find(id)
                .map(borrowedItemRepository::findAllByUser);
    }

    public Optional<List<BorrowedItem>> findAllByProduct(UUID id){
        return productRepository.find(id)
                .map(borrowedItemRepository::findAllByProduct);
    }

    public void update(BorrowedItem borrowedItem) {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)){
            borrowedItemRepository.update(borrowedItem);
            return;
        }

        User user = userService.getLoggedUser().orElseThrow();
        if (borrowedItemRepository.find(borrowedItem.getId()).map(item -> item.getUser().getId() == user.getId()).isPresent()){
            borrowedItemRepository.update(borrowedItem);
            return;
        }

        throw new NotAuthorizedException("");
    }

}
