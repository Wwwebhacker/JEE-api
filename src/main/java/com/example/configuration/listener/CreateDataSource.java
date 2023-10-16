package com.example.configuration.listener;

import com.example.datastore.component.DataStore;
import com.example.serialization.component.CloningUtility;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class CreateDataSource implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        event.getServletContext().setAttribute("datastore", new DataStore(new CloningUtility()));
    }

}
