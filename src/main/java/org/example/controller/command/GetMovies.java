package org.example.controller.command;

import org.apache.log4j.Logger;
import org.example.model.service.MovieService;
import org.example.utils.AttributeConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetMovies implements Command {
    private final Logger logger = Logger.getLogger(GetMovies.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        var movies = MovieService.getAll();

        movies.forEach(movie -> {
            logger.info(movie.getName());
        });

        request.setAttribute(AttributeConstants.MOVIES, movies);
        return "/WEB-INF/view/movie/movies.jsp";
    }
}
