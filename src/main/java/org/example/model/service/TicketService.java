package org.example.model.service;

import org.example.model.dao.DaoFactory;
import org.example.model.dao.exception.DaoException;
import org.example.model.entity.Ticket;
import org.example.model.service.exception.ServiceException;

public class TicketService {
    private static final DaoFactory daoFactory = DaoFactory.getInstance();

    public static boolean ticketExists(int movieId, int seatNumber) throws ServiceException {
        try {
            return daoFactory.createTicketDao().ticketExists(movieId, seatNumber);
        } catch (DaoException e) {
            throw new ServiceException(String.format(
                    "Failed to check whether the ticket with movieId = %d, seat = %d exists",
                    movieId,
                    seatNumber), e);
        }
    }

    public static void deleteTicket(Ticket ticket) throws ServiceException {
        try {
            daoFactory.createTicketDao().delete(ticket);
        } catch (DaoException e) {
            throw new ServiceException("Failed to delete ticket: " + ticket, e);
        }
    }

    public static void createTicket(Ticket ticket) throws ServiceException {
        try {
            daoFactory.createTicketDao().create(ticket);
        } catch (DaoException e) {
            throw new ServiceException("Failed to create a ticket: " + ticket, e);
        }
    }

    public static void updateSeat(Ticket ticket, int newSeat) throws ServiceException {
        try {
            daoFactory.createTicketDao().updateSeat(ticket, newSeat);
        } catch (DaoException e) {
            throw new ServiceException("Failed to update seat for ticket: " + ticket + " to new seat " + newSeat);
        }
    }
}
