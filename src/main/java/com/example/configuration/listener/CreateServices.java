package com.example.configuration.listener;

import com.example.datastore.component.DataStore;
import com.example.user.repository.api.UserRepository;
import com.example.user.repository.memory.UserInMemoryRepository;
import com.example.user.service.FileService;
import com.example.user.service.UserService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener

public class CreateServices implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        DataStore dataStore = (DataStore)event.getServletContext().getAttribute("datastore");
        UserRepository userRepository = new UserInMemoryRepository(dataStore);
        String filePath = event.getServletContext().getInitParameter("filePath");

        event.getServletContext().setAttribute("userService", new UserService(userRepository,new FileService(filePath)));
    }
}
