package org.example.controller.command;

import org.apache.log4j.Logger;
import org.example.model.entity.Movie;
import org.example.model.service.MovieService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateMovie implements Command {
    private static final Logger logger = Logger.getLogger(CreateMovie.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Movie movieSession = createMovieFromRequestParameters(request);

        if (movieSession != null) {
            MovieService.create(movieSession);
        }

        return "/WEB-INF/view/movie/createMovieSession.jsp";
    }

    private Movie createMovieFromRequestParameters(HttpServletRequest request) {
        String movieName = request.getParameter("movieName");
        String seatCountString = request.getParameter("seatCount");

        int seatCount = 0;

        try {
            seatCount = Integer.parseInt(seatCountString);
        } catch (NumberFormatException formatException) {

        }

        if (seatCount <= 0) {
            // TODO: error
            return null;
        }

        return new Movie(0, movieName, seatCount);
    }
}
