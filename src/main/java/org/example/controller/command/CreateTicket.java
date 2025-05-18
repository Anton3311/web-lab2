package org.example.controller.command;

import org.apache.log4j.Logger;
import org.example.controller.FrontController;
import org.example.model.dao.DaoFactory;
import org.example.model.entity.Movie;
import org.example.model.entity.Ticket;
import org.example.model.service.MovieService;
import org.example.model.service.TicketService;
import org.example.utils.*;

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

        Optional<Movie> movieFromURI = MovieService.getMovieIdFromURI(request.getRequestURI());

        if (movieFromURI.isPresent()) {
            Movie movie = movieFromURI.get();

            Ticket ticket = createTicketFromRequest(movie, request, errors);

            if (ticket != null) {
                logger.info("Created ticket " + ticket);

                response.setStatus(HttpServletResponse.SC_CREATED);
                response.sendRedirect(PagePathConstants.MOVIES_PAGE);
                return FrontController.REDIRECT;
            } else {
                logger.error("Cannot create ticket");
                errors.forEach(logger::error);

                request.setAttribute(AttributeConstants.ERRORS, errors);
                request.setAttribute(AttributeConstants.HAS_ERRORS, !errors.isEmpty());

                return ViewPathConstants.BUY_TICKET;
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return ViewPathConstants.MOVIE_NOT_FOUND;
        }
    }

    private Ticket createTicketFromRequest(Movie movie, HttpServletRequest request, ArrayList<String> errors) {
        String seatNumberString = request.getParameter(ParameterNameConstants.SEAT_NUMBER);

        request.getParameterMap().forEach((key, value) -> logger.info(key + " " + Arrays.toString(value)));

        int seatNumber;
        try {
            seatNumber = Integer.parseInt(seatNumberString);
        } catch (NumberFormatException e) {
            errors.add("Invalid seat number format");
            return null;
        }

        boolean isSeatValid = seatNumber > 0 && seatNumber <= movie.getSeatCount();
        if (!isSeatValid) {
            errors.add(String.format("Invalid seat number. Must be > 0 and <= %d", movie.getSeatCount()));
            return null;
        }

        logger.info("movie_id = " + movie.getId());
        logger.info("seat = " + seatNumber);
        boolean exists = TicketService.ticketExists(movie.getId(), seatNumber);
        logger.info(exists);
        if (exists) {
            errors.add(String.format("Seat number %d is already taken", seatNumber));
            return null;
        }

        Ticket ticket = new Ticket(movie, seatNumber);
        TicketService.createTicket(ticket);
        return ticket;
    }
}
