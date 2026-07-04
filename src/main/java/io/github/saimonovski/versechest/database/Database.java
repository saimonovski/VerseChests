package io.github.saimonovski.versechest.database;

public interface Database {
    void connect();

    void disconnect();

    void createTables();

}
