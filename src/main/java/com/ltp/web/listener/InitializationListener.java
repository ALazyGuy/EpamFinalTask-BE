package com.ltp.web.listener;

import com.ltp.web.connection.ConnectionPool;
import com.ltp.web.exception.RegistryException;
import com.ltp.web.security.registry.RolesRegistry;
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
        try {
            RolesRegistry.getInstance().create("SHARED", new String[]{
                    "/user/add", "/user/auth"
            });
            RolesRegistry.getInstance().create("ROLE_USER", new String[]{
                    "/user/current", "/people/add",
                    "/people/remove", "/people/getAll",
                    "/people/found", "/people/search",
                    "/people/getById"
            });
            RolesRegistry.getInstance().create("ROLE_ADMIN", new String[]{
                    "/user/current", "/people/add",
                    "/people/remove", "/people/getAll",
                    "/people/found", "/people/search",
                    "/people/getById"
            });
        } catch (RegistryException e) {
            LOGGER.error(String.format("Unable to create registry [%s]", e.getMessage()));
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
        try {
            ConnectionPool.getInstance().closeAll();
        } catch (SQLException e) {
            LOGGER.error("Unable to close connections");
        }
    }
}
