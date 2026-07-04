package io.github.saimonovski.versechest.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface Database extends AutoCloseable {
    void connect();

    void disconnect();

    void createTables();

    Connection getConnection() throws SQLException;

    @Override
    default void close() {
        disconnect();
    }
}
