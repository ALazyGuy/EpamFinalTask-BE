package com.ltp.web.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ConnectionPool {

    private static final int MAX_POOL_SIZE = 15;
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private static final String DB_URL = "jdbc:mysql://localhost:3306/interpol?serverTimezone=UTC";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "cocksucker";

    private final List<Connection> freeConnections;
    private final List<Connection> busyConnections;

    private ConnectionPool(){
        freeConnections = new LinkedList<>();
        busyConnections = new LinkedList<>();

        try {

            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            for (int d = 0; d < MAX_POOL_SIZE; d++) {
                freeConnections.add(DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD));
            }
        }catch(SQLException e){
            LOGGER.fatal("Unable to connect to database: " + e.getMessage());
            Thread.currentThread().interrupt();
        }

        LOGGER.info("Connection to database established successfully");
    }

    public Connection getConnection(){
        Connection connection = freeConnections.remove(freeConnections.size() - 1);
        busyConnections.add(connection);
        return connection;
    }

    public void releaseConnection(Connection connection){
        freeConnections.add(connection);
        busyConnections.remove(connection);
    }

    public void closeAll() throws SQLException {
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
