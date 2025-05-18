package org.example.model.dao;

import org.example.model.dao.exception.DaoException;
import org.example.model.entity.Movie;

import java.util.ArrayList;
import java.util.Optional;

public interface MovieDao {
    Optional<Movie> findMovieByName(String name) throws DaoException;
    Optional<Movie> findMovieById(int id) throws DaoException;
    ArrayList<Movie> findAllMovies() throws DaoException;
}
