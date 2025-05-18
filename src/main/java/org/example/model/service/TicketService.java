package org.example.model.service;

import org.example.model.dao.DaoFactory;
import org.example.model.entity.Ticket;

public class TicketService {
    private static final DaoFactory daoFactory = DaoFactory.getInstance();

    public static boolean ticketExists(int movieId, int seatNumber) {
        return daoFactory.createTicketDao().ticketExists(movieId, seatNumber);
    }

    public static void deleteTicket(Ticket ticket) {
        daoFactory.createTicketDao().delete(ticket);
    }

    public static void createTicket(Ticket ticket) {
        daoFactory.createTicketDao().create(ticket);
    }
}
