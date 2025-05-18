package org.example.model.dao;

import org.example.model.dao.exception.DaoException;
import org.example.model.entity.Ticket;

public interface TicketDao {
    boolean ticketExists(int movieId, int seatNumber) throws DaoException;
    void create(Ticket ticket) throws DaoException;
    void delete(Ticket ticket) throws DaoException;
    void updateSeat(Ticket ticket, int newSeat) throws DaoException;
}
