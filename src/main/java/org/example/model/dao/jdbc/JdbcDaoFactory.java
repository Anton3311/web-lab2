package org.example.model.dao.jdbc;

import org.apache.log4j.Logger;
import org.example.model.dao.DaoConnection;
import org.example.model.dao.DaoFactory;
import org.example.model.dao.MovieDao;
import org.example.model.dao.TicketDao;
import org.example.model.dao.exception.DaoException;

import javax.naming.InitialContext;
import javax.sql.DataSource;
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
    public DaoConnection getConnection() {
        try {
            return new JdbcDaoConnection(dataSource.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MovieDao createMovieDao(DaoConnection connection) {
        return new JdbcMovieDao(connection);
    }

    @Override
    public TicketDao createTicketDao(DaoConnection connection) {
        return new JdbcTicketDao(connection);
    }
}
