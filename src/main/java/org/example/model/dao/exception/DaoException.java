package org.example.model.dao.exception;

public class DaoException extends RuntimeException {
    public DaoException(String message) {
        super(message);
    }

    public DaoException(Exception cause) {
        super("Dau Error", cause);
    }
}
