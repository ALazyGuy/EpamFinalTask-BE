package com.ltp.web.connection;

import com.ltp.web.exception.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionPool {

    private static final int MAX_POOL_SIZE = 15;
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private static final String DB_URL = "jdbc:mysql://localhost:3306/interpol?serverTimezone=UTC";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "cocksucker";

    private final BlockingQueue<Connection> freeConnections;
    private final Queue<Connection> busyConnections;

    private ConnectionPool(){
        freeConnections = new LinkedBlockingQueue<>(MAX_POOL_SIZE);
        busyConnections = new ArrayDeque<>();

        try {

            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            for (int d = 0; d < MAX_POOL_SIZE; d++) {
                freeConnections.offer(DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD));
            }
        }catch(SQLException e){
            LOGGER.fatal("Unable to connect to database: " + e.getMessage());
            Thread.currentThread().interrupt();
        }

        LOGGER.info("Connection to database established successfully");
    }

    public Connection getConnection() throws ConnectionPoolException {
        if(freeConnections.size() == 0){
            throw new ConnectionPoolException("No free connections");
        }
        Connection connection = null;

        try{
            connection = freeConnections.take();
        }catch(InterruptedException e){
            throw new ConnectionPoolException("Unable to load connection from connection pool");
        }

        busyConnections.offer(connection);
        return connection;
    }

    public void releaseConnection(Connection connection) throws ConnectionPoolException{
        if(!busyConnections.contains(connection) || freeConnections.size() == MAX_POOL_SIZE){
            throw new ConnectionPoolException("Unable to release unknown connection");
        }
        freeConnections.offer(connection);
        busyConnections.remove(connection);
    }

    public void destroy() throws SQLException {
        closeAll();

        DriverManager.getDrivers().asIterator().forEachRemaining(
                e -> {
                    try {
                        DriverManager.deregisterDriver(e);
                    } catch (SQLException ex) {
                        LOGGER.fatal("Unable to deregister drivers");
                        Thread.currentThread().interrupt();
                    }
                }
        );
    }

    private void closeAll() throws SQLException {
        for(Connection c : freeConnections){
            c.close();
        }

        for(Connection c : busyConnections){
            c.close();
        }

    }

    public static ConnectionPool getInstance(){
        return ConnectionPoolHolder.INSTANCE;
    }

    private static final class ConnectionPoolHolder{
        public static final ConnectionPool INSTANCE = new ConnectionPool();
    }

}
