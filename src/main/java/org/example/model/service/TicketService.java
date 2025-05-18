package org.example.model.service;

import org.example.model.dao.DaoConnection;
import org.example.model.dao.DaoFactory;
import org.example.model.entity.Ticket;
import org.example.model.service.exception.ServiceException;

public class TicketService {
    private static final DaoFactory daoFactory = DaoFactory.getInstance();

    public static boolean ticketExists(int movieId, int seatNumber) throws ServiceException {
        try (DaoConnection connection = DaoFactory.getInstance().getConnection()) {
            return daoFactory.createTicketDao(connection).ticketExists(movieId, seatNumber);
        } catch (Exception e) {
            throw new ServiceException(String.format(
                    "Failed to check whether the ticket with movieId = %d, seat = %d exists",
                    movieId,
                    seatNumber), e);
        }
    }

    public static void deleteTicket(Ticket ticket) throws ServiceException {
        try (DaoConnection connection = DaoFactory.getInstance().getConnection()) {
            daoFactory.createTicketDao(connection).delete(ticket);
        } catch (Exception e) {
            throw new ServiceException("Failed to delete ticket: " + ticket, e);
        }
    }

    public static void createTicket(Ticket ticket) throws ServiceException {
        try (DaoConnection connection = DaoFactory.getInstance().getConnection()) {
            daoFactory.createTicketDao(connection).create(ticket);
        } catch (Exception e) {
            throw new ServiceException("Failed to create a ticket: " + ticket, e);
        }
    }

    public static void updateSeat(Ticket ticket, int newSeat) throws ServiceException {
        try (DaoConnection connection = DaoFactory.getInstance().getConnection()) {
            daoFactory.createTicketDao(connection).updateSeat(ticket, newSeat);
        } catch (Exception e) {
            throw new ServiceException("Failed to update seat for ticket: " + ticket + " to new seat " + newSeat);
        }
    }
}
