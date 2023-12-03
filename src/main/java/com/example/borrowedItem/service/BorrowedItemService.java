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
import lombok.extern.java.Log;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
@Log
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



    public Optional<BorrowedItem> find(UUID id) {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)){
            return borrowedItemRepository.find(id);
        }

        User user = userService.getLoggedUser().orElseThrow();

        return borrowedItemRepository.findByIdAndUser(id, user);
    }

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
        log.log(Level.WARNING, "User " + user.getLogin() + " created item with id " + borrowedItem.getId());
    }

    public void delete(UUID id) {
        User user = userService.getLoggedUser().orElseThrow();


        if(securityContext.isCallerInRole(UserRoles.ADMIN)){
            borrowedItemRepository.delete(borrowedItemRepository.find(id).orElseThrow());
        } else {

            BorrowedItem borrowedItem = user.getItems().stream().filter(item -> item.getId() == id).findFirst().orElseThrow();
            borrowedItemRepository.delete(borrowedItem);
        }
        log.log(Level.WARNING, "User " + user.getLogin() + " deleted item with id " + id);

    }


    public Optional<List<BorrowedItem>> findAllByUser(UUID id){
        return userRepository.find(id)
                .map(borrowedItemRepository::findAllByUser);
    }

    public Optional<List<BorrowedItem>> findAllByProduct(UUID id){
        if (securityContext.isCallerInRole(UserRoles.ADMIN)){
            return productRepository.find(id)
                    .map(borrowedItemRepository::findAllByProduct);
        }

        User user = userService.getLoggedUser().orElseThrow();

        var items = productRepository.find(id)
                .map(borrowedItemRepository::findAllByProduct);

        if (items.isPresent()){
            return Optional.of(items.get().stream().filter(borrowedItem -> borrowedItem.getUser().getId() == user.getId()).toList());
        }

        return items;
    }

    public void update(BorrowedItem borrowedItem) {
        User user = userService.getLoggedUser().orElseThrow();


        if (securityContext.isCallerInRole(UserRoles.ADMIN)){
            borrowedItemRepository.update(borrowedItem);
        } else if (borrowedItemRepository.find(borrowedItem.getId()).map(item -> item.getUser().getId() == user.getId()).isPresent()) {
            borrowedItemRepository.update(borrowedItem);
        } else {
            throw new NotAuthorizedException("");
        }

        log.log(Level.WARNING, "User " + user.getLogin() + " updated item with id " + borrowedItem.getId());



    }

}
