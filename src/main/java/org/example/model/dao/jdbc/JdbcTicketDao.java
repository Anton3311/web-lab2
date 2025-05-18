package org.example.model.dao.jdbc;

import org.example.model.dao.TicketDao;
import org.example.model.dao.exception.DaoException;
import org.example.model.entity.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTicketDao implements TicketDao {
    private final Connection connection;

    public JdbcTicketDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean ticketExists(int movieId, int seatNumber) {
        try (PreparedStatement statement = connection.prepareStatement("select count(movie_id) from tickets where tickets.movie_id = ? and tickets.seat = ?")) {
            statement.setInt(1, movieId);
            statement.setInt(2, seatNumber);

            ResultSet count = statement.executeQuery();
            if (count.next()) {
                return count.getInt(1) != 0;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void create(Ticket ticket) {
        try (PreparedStatement statement = connection.prepareStatement("insert into tickets (movie_id, seat) values (?, ?)")) {
            statement.setInt(1, ticket.getMovie().getId());
            statement.setInt(2, ticket.getSeatNumber());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(Ticket ticket) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement("delete from tickets where tickets.movie_id = ? and tickets.seat = ?")) {
            statement.setInt(1, ticket.getMovie().getId());
            statement.setInt(2, ticket.getSeatNumber());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
