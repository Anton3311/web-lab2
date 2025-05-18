package org.example.model.dao;

import java.sql.Connection;

public interface DaoConnection extends AutoCloseable {
    Connection getConnection();
}
