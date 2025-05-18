package org.example.controller.command;

import org.example.controller.FrontController;
import org.example.model.dao.DaoFactory;
import org.example.model.entity.Movie;
import org.example.model.entity.Ticket;
import org.example.model.service.MovieService;
import org.example.model.service.TicketService;
import org.example.model.service.exception.ServiceException;
import org.example.utils.*;
import org.w3c.dom.Attr;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Optional;

public class UpdateTicket implements Command {
    private class TicketUpdateData {
        private int oldSeat;
        private int newSeat;

        private TicketUpdateData(int oldSeat, int newSeat) {
            this.oldSeat = oldSeat;
            this.newSeat = newSeat;
        }

        public int getOldSeat() {
            return oldSeat;
        }

        public int getNewSeat() {
            return newSeat;
        }
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<Movie> movie = MovieService.getMovieIdFromURI(request.getRequestURI());
        if (movie.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return ViewPathConstants.MOVIE_NOT_FOUND;
        }

        request.setAttribute(AttributeConstants.MOVIE_NAME, movie.get().getName());

        ArrayList<String> errors = new ArrayList<>();
        TicketUpdateData updateParameters = getUpdateDataFromRequest(request, errors);

        if (updateParameters != null) {
            if (validateUpdateParameters(updateParameters, movie.get(), errors)) {
                try {
                    TicketService.updateSeat(new Ticket(movie.get(), updateParameters.getOldSeat()), updateParameters.getNewSeat());
                } catch (ServiceException e) {
                    errors.add(e.getMessage());
                }
            }
        }

        if (!errors.isEmpty()) {
            request.setAttribute(AttributeConstants.HAS_ERRORS, true);
            request.setAttribute(AttributeConstants.ERRORS, errors);
            return ViewPathConstants.UPDATE_TICKET;
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect(PagePathConstants.MOVIES_PAGE);
        return FrontController.REDIRECT;
    }

    private boolean validateUpdateParameters(TicketUpdateData updateParameters, Movie movie, ArrayList<String> errors) {
        boolean oldTicketExists = TicketService.ticketExists(movie.getId(), updateParameters.getOldSeat());

        if (!oldTicketExists) {
            errors.add(String.format("Ticket to a movie '%s' with a seat %d doesn't exist",
                    movie.getName(),
                    updateParameters.getOldSeat()));
            return false;
        }

        boolean newTicketExists = TicketService.ticketExists(movie.getId(), updateParameters.getNewSeat());
        if (newTicketExists) {
            errors.add(String.format("Ticket to a movie '%s' with a seat %d is already taken",
                    movie.getName(),
                    updateParameters.getNewSeat()));
            return false;
        }

        return true;
    }

    private TicketUpdateData getUpdateDataFromRequest(HttpServletRequest request, ArrayList<String> errors) {
        String oldSeatString = request.getParameter(ParameterNameConstants.OLD_SEAT_NUMBER);
        String newSeatString = request.getParameter(ParameterNameConstants.NEW_SEAT_NUMBER);

        int oldSeat;
        int newSeat;

        try {
            oldSeat = Integer.parseInt(oldSeatString);
        } catch (NumberFormatException e) {
            errors.add("Invalid format for the old seat number");
            return null;
        }

        try {
            newSeat = Integer.parseInt(newSeatString);
        } catch (NumberFormatException e) {
            errors.add("Invalid format for the new seat number");
            return null;
        }

        return new TicketUpdateData(oldSeat, newSeat);
    }
}
