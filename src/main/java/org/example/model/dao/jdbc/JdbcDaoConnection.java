package org.example.model.dao.jdbc;

import org.apache.log4j.Logger;
import org.example.model.dao.DaoConnection;
import org.example.model.dao.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcDaoConnection implements DaoConnection {
    private static final Logger logger = Logger.getLogger(JdbcDaoConnection.class);
    private final Connection connection;

    public JdbcDaoConnection(Connection connection) {
        this.connection = connection;

        logger.info("Created connection");
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void close() {
        try {
            connection.close();

            logger.info("Closed connection");
        } catch (SQLException e) {
            throw new DaoException("Failed to close connection", e);
        }
    }
}
