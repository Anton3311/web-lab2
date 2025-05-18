package org.example.model.service;

import org.example.model.dao.DaoConnection;
import org.example.model.dao.DaoFactory;
import org.example.model.dao.MovieDao;
import org.example.model.dao.exception.DaoException;
import org.example.model.entity.Movie;
import org.example.model.service.exception.ServiceException;

import java.util.ArrayList;
import java.util.Optional;

public class MovieService {
    private static final DaoFactory daoFactory = DaoFactory.getInstance();

    public static Optional<Movie> getById(int id) throws ServiceException {
        try (DaoConnection connection = DaoFactory.getInstance().getConnection()) {
            return daoFactory.createMovieDao(connection).findMovieById(id);
        } catch (Exception e) {
            throw new ServiceException(String.format("Failed to get a movie with id = %d", id), e);
        }
    }

    public static Optional<Movie> getByName(String name) {
        try (DaoConnection connection = DaoFactory.getInstance().getConnection()) {
            return daoFactory.createMovieDao(connection).findMovieByName(name);
        } catch (Exception e) {
            throw new ServiceException(String.format("Failed to find a movie named '%s'", name), e);
        }
    }

    public static ArrayList<Movie> getAll() {
        try (DaoConnection connection = DaoFactory.getInstance().getConnection()) {
            return daoFactory.createMovieDao(connection).findAllMovies();
        } catch (Exception e) {
            throw new ServiceException("Failed to get all movies", e);
        }
    }

    public static Optional<Movie> getMovieIdFromURI(String uri) throws ServiceException {
        try (DaoConnection connection = DaoFactory.getInstance().getConnection()) {
            MovieDao movieDao = DaoFactory.getInstance().createMovieDao(connection);
            int movieId = Integer.parseInt(uri.substring(uri.lastIndexOf("/") + 1));
            return movieDao.findMovieById(movieId);
        } catch (Exception e) {
            throw new ServiceException("Failed to extract movie from URI", e);
        }
    }
}
