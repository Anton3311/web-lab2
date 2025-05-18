package org.example.controller.command;

import org.example.model.service.MovieService;
import org.example.utils.AttributeConstants;
import org.example.utils.ViewPathConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetMovies implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        var movies = MovieService.getAll();

        request.setAttribute(AttributeConstants.MOVIES, movies);
        return ViewPathConstants.MOVIES;
    }
}
