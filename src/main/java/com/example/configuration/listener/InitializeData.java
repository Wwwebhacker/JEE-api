package com.example.configuration.listener;

import com.example.borrowedItem.entity.BorrowedItem;
import com.example.borrowedItem.repository.api.BorrowedItemRepository;
import com.example.borrowedItem.service.BorrowedItemService;
import com.example.product.entity.Product;
import com.example.product.service.ProductService;
import com.example.user.entity.User;
import com.example.user.entity.UserRoles;
import com.example.user.service.UserService;
import jakarta.annotation.security.RunAs;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.control.RequestContextController;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@ApplicationScoped
public class InitializeData {

    private final UserService userService;
    private final ProductService productService;
    private final BorrowedItemRepository borrowedItemRepository;
    private final RequestContextController requestContextController;


    @Inject
    public InitializeData(BorrowedItemRepository borrowedItemRepository, ProductService productService, UserService userService, RequestContextController requestContextController){
        this.productService = productService;
        this.userService = userService;
        this.borrowedItemRepository = borrowedItemRepository;
        this.requestContextController = requestContextController;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }


    private void init() {
        requestContextController.activate();
        if(productService.findAll().isEmpty()) {
            User user = User.builder()
                    .id(UUID.fromString("c4804e0f-769e-4ab9-9ebe-0578fb4f00a6"))
                    .name("user")
                    .login("u")
                    .password("u")
                    .registrationDate(LocalDate.now())
                    .roles(List.of(UserRoles.USER))
                    .build();

            userService.create(user);


            for (int i = 0; i < 3; i++) {
                user = User.builder()
                        .id(UUID.randomUUID())
                        .name("user" + i)
                        .login("u" + i)
                        .password("u")
                        .registrationDate(LocalDate.now())
                        .roles(List.of(UserRoles.USER))
                        .build();
                userService.create(user);
            }

            Product product = Product.builder()
                    .id(UUID.fromString("ff327e8a-77c0-4f9b-90a2-89e16895d1e1"))
                    .name("product--")
                    .brand("brand--")
                    .build();
            productService.create(product);

            for (int i = 0; i < 3; i++) {
                product = Product.builder()
                        .id(UUID.randomUUID())
                        .name("product" + i)
                        .brand("brand" + i)
                        .build();
                productService.create(product);
            }


            for (int i = 0; i < 8; i++) {
                user = userService.findAll().get(i % 4);
                product = productService.findAll().get(i % 4);

                BorrowedItem borrowedItem = BorrowedItem.builder()
                        .id(UUID.randomUUID())
                        .date(LocalDate.now())
                        .user(user)
                        .product(product)
                        .build();
                borrowedItemRepository.create(borrowedItem);
            }

        }

        requestContextController.deactivate();
    }
}
