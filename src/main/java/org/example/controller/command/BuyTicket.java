package org.example.controller.command;

import org.example.model.service.MovieService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.example.model.entity.Movie;
import org.example.utils.AttributeConstants;

public class BuyTicket implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<Movie> movie = Optional.empty();

        try {
            String uri = request.getRequestURI();
            int movieId = Integer.parseInt(uri.substring(uri.lastIndexOf("/") + 1));

            movie = MovieService.getById(movieId);
        } catch (NumberFormatException _) {
        }

        if (movie.isPresent()) {
            request.setAttribute(AttributeConstants.MOVIE_NAME, movie.get().getName());
            return "/WEB-INF/view/ticket/buyTicket.jsp";
        } else {
            return "/WEB-INF/view/error/movieNotFound.jsp";
        }
    }
}
