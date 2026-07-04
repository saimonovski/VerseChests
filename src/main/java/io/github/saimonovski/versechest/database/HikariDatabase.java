package io.github.saimonovski.versechest.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.saimonovski.versechest.util.Logger;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class HikariDatabase implements Database {
    private final JavaPlugin plugin;
    private final Logger logger;
    private HikariDataSource dataSource;

    public HikariDatabase(JavaPlugin plugin, Logger logger) {
        this.plugin = plugin;
        this.logger = logger;
    }

    @Override
    public void connect() {
        if (this.dataSource != null && !this.dataSource.isClosed()) {
            return;
        }

        try {
            Files.createDirectories(this.plugin.getDataFolder().toPath());
        } catch (IOException exception) {
            throw new IllegalStateException("Could not create plugin data folder", exception);
        }

        Path databasePath = this.plugin.getDataFolder().toPath().resolve("versechests.db");
        HikariConfig config = new HikariConfig();
        config.setPoolName("VerseChests-SQLite");
        config.setJdbcUrl("jdbc:sqlite:" + databasePath.toAbsolutePath());
        config.setMaximumPoolSize(1);
        config.setConnectionTestQuery("SELECT 1");
        config.addDataSourceProperty("journal_mode", "WAL");
        config.addDataSourceProperty("foreign_keys", "true");

        this.dataSource = new HikariDataSource(config);
        this.logger.info("Connected to SQLite database: " + databasePath.toAbsolutePath());
    }

    @Override
    public void disconnect() {
        if (this.dataSource == null || this.dataSource.isClosed()) {
            return;
        }

        this.dataSource.close();
        this.logger.info("Database connection closed");
    }

    @Override
    public void createTables() {
        String createChestsTable = """
                CREATE TABLE IF NOT EXISTS chests (
                    chest_id TEXT PRIMARY KEY,
                    world_id TEXT NOT NULL,
                    x INTEGER NOT NULL,
                    y INTEGER NOT NULL,
                    z INTEGER NOT NULL,
                    UNIQUE(world_id, x, y, z)
                )
                """;

        String createChestCooldownsTable = """
                CREATE TABLE IF NOT EXISTS chest_cooldowns (
                    chest_id TEXT NOT NULL,
                    player_id TEXT NOT NULL,
                    expires_at INTEGER NOT NULL,
                    PRIMARY KEY (chest_id, player_id),
                    FOREIGN KEY (chest_id) REFERENCES chests(chest_id) ON DELETE CASCADE
                )
                """;

        String createPlayersTable = """
                CREATE TABLE IF NOT EXISTS players (
                    player_id TEXT PRIMARY KEY,
                    opened_chests INTEGER NOT NULL DEFAULT 0
                )
                """;

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("PRAGMA foreign_keys = ON");
            statement.execute(createChestsTable);
            statement.execute(createChestCooldownsTable);
            statement.execute(createPlayersTable);
            statement.execute("CREATE INDEX IF NOT EXISTS idx_chest_cooldowns_expires_at ON chest_cooldowns(expires_at)");
        } catch (SQLException exception) {
            throw new IllegalStateException("Could not create database tables", exception);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (this.dataSource == null || this.dataSource.isClosed()) {
            throw new SQLException("Database is not connected");
        }

        return this.dataSource.getConnection();
    }
}
