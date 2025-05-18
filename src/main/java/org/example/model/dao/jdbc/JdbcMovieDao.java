package org.example.model.dao.jdbc;

import org.example.model.dao.MovieDao;
import org.example.model.dao.exception.DaoException;
import org.example.model.entity.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class JdbcMovieDao implements MovieDao {
    private static final int ID_COLUMN_INDEX = 1;
    private static final int NAME_COLUMN_INDEX = 2;
    private static final int SEAT_COUNT_COLUMN_INDEX = 3;
    private final Connection connection;

    public JdbcMovieDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Movie> findMovieByName(String name) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement("select * from movies where movies.name = ? limit 1")) {
            statement.setString(1, name);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return Optional.of(new Movie(
                        result.getInt(ID_COLUMN_INDEX),
                        result.getString(NAME_COLUMN_INDEX),
                        result.getInt(SEAT_COUNT_COLUMN_INDEX)));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Movie> findMovieById(int id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement("select * from movies where movies.id = ? limit 1")) {
            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return Optional.of(new Movie(
                        result.getInt(ID_COLUMN_INDEX),
                        result.getString(NAME_COLUMN_INDEX),
                        result.getInt(SEAT_COUNT_COLUMN_INDEX)));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public ArrayList<Movie> findAllMovies() throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement("select * from movies")) {
            ResultSet results = statement.executeQuery();

            ArrayList<Movie> movies = new ArrayList<>();

            while (results.next()) {
                Movie movie = new Movie(
                        results.getInt(ID_COLUMN_INDEX),
                        results.getString(NAME_COLUMN_INDEX),
                        results.getInt(SEAT_COUNT_COLUMN_INDEX));

                movies.add(movie);
            }

            return movies;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
