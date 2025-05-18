package org.example.model.dao.exception;

public class DaoException extends RuntimeException {
    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Exception cause) {
        super(message, cause);
    }

    public DaoException(Exception cause) {
        super("Dau Error", cause);
    }
}
