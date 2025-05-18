package org.example.controller.command;

import org.example.model.dao.DaoFactory;
import org.example.model.entity.Movie;
import org.example.model.service.MovieService;
import org.example.utils.AttributeConstants;
import org.example.utils.URIUtils;
import org.example.utils.ViewPathConstants;
import org.w3c.dom.Attr;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class ReturnTicket implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<Movie> movie = MovieService.getMovieIdFromURI(request.getRequestURI());

        if (movie.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return ViewPathConstants.MOVIE_NOT_FOUND;
        }

        request.setAttribute(AttributeConstants.MOVIE_NAME, movie.get().getName());
        request.setAttribute(AttributeConstants.HAS_ERRORS, false);
        request.setAttribute(AttributeConstants.ERRORS, new ArrayList<String>());

        return ViewPathConstants.RETURN_TICKET;
    }
}
