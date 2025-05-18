package org.example.controller.command;

import org.apache.log4j.Logger;
import org.example.controller.FrontController;
import org.example.model.dao.DaoFactory;
import org.example.model.entity.Movie;
import org.example.model.entity.Ticket;
import org.example.model.service.TicketService;
import org.example.utils.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class DeleteTicket implements Command {
    private final Logger logger = Logger.getLogger(DeleteTicket.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<Movie> movie = URIUtils.getMovieIdFromURI(request.getRequestURI(), DaoFactory.getInstance().createMovieDao());

        if (movie.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return ViewPathConstants.MOVIE_NOT_FOUND;
        }

        ArrayList<String> errors = new ArrayList<>();
        Ticket ticket = createTicketFromRequest(request, errors, movie.get());

        if (ticket != null) {
            TicketService.deleteTicket(ticket);

            response.sendRedirect(PagePathConstants.MOVIES_PAGE);
            return FrontController.REDIRECT;
        } else {
            request.setAttribute(AttributeConstants.MOVIE_NAME, movie.get().getName());
            request.setAttribute(AttributeConstants.HAS_ERRORS, !errors.isEmpty());
            request.setAttribute(AttributeConstants.ERRORS, errors);
            return ViewPathConstants.RETURN_TICKET;
        }
    }

    private Ticket createTicketFromRequest(HttpServletRequest request, ArrayList<String> errors, Movie movie) {
        String seatString = request.getParameter(ParameterNameConstants.SEAT_NUMBER);

        try {
            int seat = Integer.parseInt(seatString);

            boolean isTicketValid = TicketService.ticketExists(movie.getId(), seat);
            if (!isTicketValid) {
                errors.add("This ticket is invalid");
                return null;
            }

            return new Ticket(movie, seat);
        } catch (NumberFormatException e) {
            errors.add("Invalid format for the seat number");
            return null;
        }
    }
}
