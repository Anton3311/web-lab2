package org.example.controller.command;

import org.apache.log4j.Logger;
import org.example.model.dao.DaoFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import org.example.model.entity.Movie;
import org.example.utils.AttributeConstants;
import org.example.utils.URIUtils;
import org.example.utils.ViewPathConstants;

public class BuyTicket implements Command {
    private final Logger logger = Logger.getLogger(BuyTicket.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<Movie> movie = URIUtils.getMovieIdFromURI(request.getRequestURI(), DaoFactory.getInstance().createMovieDao());

        if (movie.isPresent()) {
            request.setAttribute(AttributeConstants.MOVIE_NAME, movie.get().getName());

            logger.info(movie.get().getName());
            return ViewPathConstants.BUY_TICKET;
        } else {
            return ViewPathConstants.MOVIE_NOT_FOUND;
        }
    }
}
