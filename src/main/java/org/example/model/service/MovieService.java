package org.example.model.service;

import org.example.model.dao.DaoFactory;
import org.example.model.entity.Movie;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class MovieService {
    private static final DaoFactory daoFactory = DaoFactory.getInstance();

    public static void create(Movie movieSession) {
        try (PreparedStatement statement = daoFactory.getConnection().prepareStatement("insert into movies (movie_name, seat_count) values (?, ?)")){
            statement.setString(0, movieSession.getName());
            statement.setInt(1, movieSession.getSeatCount());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Optional<Movie> getById(int id) {
        return daoFactory.createMovieDao().findMovieById(id);
    }

    public static Optional<Movie> getByName(String name) {
        return daoFactory.createMovieDao().findMovieByName(name);
    }

    public static ArrayList<Movie> getAll() {
        return daoFactory.createMovieDao().findAllMovies();
    }
}
