package org.example.controller.command;

import org.apache.log4j.Logger;
import org.example.model.entity.Movie;
import org.example.model.entity.Ticket;
import org.example.model.service.MovieService;
import org.example.model.service.TicketService;
import org.example.utils.AttributeConstants;
import org.example.utils.ParameterNameConstants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class CreateTicket implements Command {
    private final Logger logger = Logger.getLogger(CreateTicket.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<String> errors = new ArrayList<>();
        Ticket ticket = createTicketFromRequest(request, errors);

        if (ticket != null) {
            logger.info("Created ticket " + ticket);

            response.setStatus(HttpServletResponse.SC_CREATED);
            return "";
        } else {
            logger.error("Cannot create ticket");
            errors.forEach(logger::error);

            request.setAttribute(AttributeConstants.ERRORS, errors);
            request.setAttribute(AttributeConstants.HAS_ERRORS, !errors.isEmpty());

            return "/WEB-INF/view/ticket/buyTicket.jsp";
        }
    }

    private Ticket createTicketFromRequest(HttpServletRequest request, ArrayList<String> errors) {
        String movieName = request.getParameter(ParameterNameConstants.MOVIE_NAME);
        String seatNumberString = request.getParameter(ParameterNameConstants.SEAT_NUMBER);

        request.getParameterMap().forEach((key, value) -> logger.info(key + " " + Arrays.toString(value)));

        int seatNumber;
        try {
            seatNumber = Integer.parseInt(seatNumberString);
        } catch (NumberFormatException e) {
            errors.add("Invalid seat number format");
            return null;
        }

        Optional<Movie> movie = MovieService.getByName(movieName);
        if (movie.isEmpty()) {
            errors.add(String.format("Movie named '%s' doesn't exist", movieName));
            return null;
        }

        boolean isSeatValid = seatNumber > 0 && seatNumber <= movie.get().getSeatCount();
        if (!isSeatValid) {
            errors.add(String.format("Invalid seat number. Must be > 0 and <= %d", movie.get().getSeatCount()));
            return null;
        }

        if (TicketService.ticketExists(movie.get().getId(), seatNumber)) {
            errors.add(String.format("Seat number %d is already taken", seatNumber));
            return null;
        }

        Ticket ticket = new Ticket(movie.get(), seatNumber);
        TicketService.createTicket(ticket);
        return ticket;
    }
}
