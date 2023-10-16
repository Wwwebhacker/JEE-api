package com.example.configuration.listener;

import com.example.user.entity.User;
import com.example.user.entity.UserRoles;
import com.example.user.service.UserService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.SneakyThrows;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@WebListener
public class InitializeData implements ServletContextListener {

    private UserService userService;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        userService = (UserService) event.getServletContext().getAttribute("userService");

        init();
    }

    @SneakyThrows
    private void init() {
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




    }
}
