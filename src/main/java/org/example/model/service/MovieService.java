package org.example.model.service;

import org.example.model.dao.DaoFactory;
import org.example.model.dao.exception.DaoException;
import org.example.model.entity.Movie;
import org.example.model.service.exception.ServiceException;

import java.util.ArrayList;
import java.util.Optional;

public class MovieService {
    private static final DaoFactory daoFactory = DaoFactory.getInstance();

    public static Optional<Movie> getById(int id) throws ServiceException {
        try {
            return daoFactory.createMovieDao().findMovieById(id);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Failed to get a movie with id = %d", id), e);
        }
    }

    public static Optional<Movie> getByName(String name) {
        try {
            return daoFactory.createMovieDao().findMovieByName(name);
        } catch (DaoException e) {
            throw new ServiceException(String.format("Failed to find a movie named '%s'", name), e);
        }
    }

    public static ArrayList<Movie> getAll() {
        try {
            return daoFactory.createMovieDao().findAllMovies();
        } catch (DaoException e) {
            throw new ServiceException("Failed to get all movies", e);
        }
    }
}
