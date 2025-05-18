package org.example.utils;

import org.example.model.dao.MovieDao;
import org.example.model.entity.Movie;

import java.util.Optional;

public class URIUtils {
    public static Optional<Movie> getMovieIdFromURI(String uri, MovieDao movieDao) {
        try {
            int movieId = Integer.parseInt(uri.substring(uri.lastIndexOf("/") + 1));
            return movieDao.findMovieById(movieId);
        } catch (NumberFormatException _) {
        }

        return Optional.empty();
    }
}
