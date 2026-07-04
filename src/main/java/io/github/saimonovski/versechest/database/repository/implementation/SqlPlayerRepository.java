package io.github.saimonovski.versechest.database.repository.implementation;

import io.github.saimonovski.versechest.database.Database;
import io.github.saimonovski.versechest.database.repository.PlayerRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SqlPlayerRepository implements PlayerRepository {
    private final Database database;

    public SqlPlayerRepository(Database database) {
        this.database = database;
    }

    @Override
    public int findPlayerCount(UUID playerId) {
        String sql = "SELECT opened_chests FROM players WHERE player_id = ?";

        try (Connection connection = this.database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, playerId.toString());
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? resultSet.getInt("opened_chests") : 0;
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("Could not find player count " + playerId, exception);
        }
    }

    @Override
    public Map<UUID, Integer> findAllPlayerCounts() {
        String sql = "SELECT player_id, opened_chests FROM players";
        Map<UUID, Integer> playerCounts = new HashMap<>();

        try (Connection connection = this.database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                playerCounts.put(UUID.fromString(resultSet.getString("player_id")), resultSet.getInt("opened_chests"));
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("Could not load player counts", exception);
        }

        return playerCounts;
    }

    @Override
    public void updatePlayerCount(UUID playerId, int playerCount) {
        String sql = """
                INSERT INTO players (player_id, opened_chests)
                VALUES (?, ?)
                ON CONFLICT(player_id) DO UPDATE SET opened_chests = excluded.opened_chests
                """;

        try (Connection connection = this.database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, playerId.toString());
            statement.setInt(2, Math.max(playerCount, 0));
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new IllegalStateException("Could not update player count " + playerId, exception);
        }
    }

    @Override
    public void removePlayerCount(UUID playerId) {
        String sql = "DELETE FROM players WHERE player_id = ?";

        try (Connection connection = this.database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, playerId.toString());
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new IllegalStateException("Could not remove player count " + playerId, exception);
        }
    }
}
