package com.example.configuration.listener;

import com.example.component.DtoFunctionFactory;
import com.example.user.controller.UserController;
import com.example.user.service.UserService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class CreateControllers implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event){
        UserService userService = (UserService) event.getServletContext().getAttribute("userService");

        event.getServletContext().setAttribute("userController", new UserController(userService, new DtoFunctionFactory()));
    }
}
