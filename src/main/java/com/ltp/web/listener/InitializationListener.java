package com.ltp.web.listener;

import com.ltp.web.connection.ConnectionPool;
import com.ltp.web.security.SecurityContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

@WebListener
public class InitializationListener implements ServletContextListener {

    private static final Logger LOGGER = LogManager.getLogger(InitializationListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContextListener.super.contextInitialized(sce);
        ConnectionPool.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
        SecurityContext.getInstance().deauthenticate();
        try {
            ConnectionPool.getInstance().closeAll();
        } catch (SQLException e) {
            LOGGER.error("Unable to close connections");
        }
    }
}
