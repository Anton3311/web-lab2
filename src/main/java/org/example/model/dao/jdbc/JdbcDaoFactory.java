package org.example.model.dao.jdbc;

import org.apache.log4j.Logger;
import org.example.model.dao.DaoFactory;
import org.example.model.dao.exception.DaoException;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcDaoFactory extends DaoFactory {
    private final Logger logger = Logger.getLogger(JdbcDaoFactory.class);
    private final DataSource dataSource;

    public JdbcDaoFactory() {
        try {
            InitialContext initialContext = new InitialContext();
            dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/cinema_db");
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
