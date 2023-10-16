package com.example.configuration.listener;

import com.example.user.entity.User;
import com.example.user.entity.UserRoles;
import com.example.user.service.UserService;
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
    private final RequestContextController requestContextController;


    @Inject
    public InitializeData(UserService userService, RequestContextController requestContextController){
        this.userService = userService;
        this.requestContextController = requestContextController;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }


    private void init() {
        requestContextController.activate();
        User user = User.builder()
                .id(UUID.fromString("c4804e0f-769e-4ab9-9ebe-0578fb4f00a6"))
                .name("testUser")
                .login("userLogin")
                .registrationDate(LocalDate.now())
                .roles(List.of(UserRoles.USER))
                .build();

        userService.create(user);

        for (int i = 0; i < 3; i++) {
            user = User.builder()
                    .id(UUID.randomUUID())
                    .name("user" + i)
                    .login("userLogin" + i)
                    .registrationDate(LocalDate.now())
                    .roles(List.of(UserRoles.USER))
                    .build();
            userService.create(user);
        }
        requestContextController.deactivate();
    }
}
