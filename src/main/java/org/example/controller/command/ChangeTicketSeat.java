package org.example.controller.command;

import org.example.model.entity.Movie;
import org.example.model.service.MovieService;
import org.example.utils.AttributeConstants;
import org.example.utils.ViewPathConstants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class ChangeTicketSeat implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<Movie> movie = MovieService.getMovieIdFromURI(request.getRequestURI());

        if (movie.isEmpty()) {
            return ViewPathConstants.MOVIE_NOT_FOUND;
        }

        request.setAttribute(AttributeConstants.MOVIE_NAME, movie.get().getName());
        return ViewPathConstants.UPDATE_TICKET;
    }
}
