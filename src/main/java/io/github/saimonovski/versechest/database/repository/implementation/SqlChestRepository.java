package io.github.saimonovski.versechest.database.repository.implementation;

import io.github.saimonovski.versechest.database.Database;
import io.github.saimonovski.versechest.database.model.ChestModel;
import io.github.saimonovski.versechest.database.repository.ChestRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SqlChestRepository implements ChestRepository {
    private final Database database;

    public SqlChestRepository(Database database) {
        this.database = database;
    }

    @Override
    public void save(ChestModel chestModel) {
        String sql = """
                INSERT INTO chests (chest_id, world_id, x, y, z)
                VALUES (?, ?, ?, ?, ?)
                ON CONFLICT(chest_id) DO UPDATE SET
                    world_id = excluded.world_id,
                    x = excluded.x,
                    y = excluded.y,
                    z = excluded.z
                """;

        try (Connection connection = this.database.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                fillChestStatement(statement, chestModel);
                statement.executeUpdate();
            }

            saveCooldowns(connection, chestModel.chestId(), chestModel.serializedCooldowns());
            connection.commit();
        } catch (SQLException exception) {
            throw new IllegalStateException("Could not save chest " + chestModel.chestId(), exception);
        }
    }

    private void saveCooldowns(Connection connection, UUID chestId, Map<UUID, Instant> cooldowns) throws SQLException {
        String deleteSql = "DELETE FROM chest_cooldowns WHERE chest_id = ?";
        String insertSql = """
                INSERT INTO chest_cooldowns (chest_id, player_id, expires_at)
                VALUES (?, ?, ?)
                ON CONFLICT(chest_id, player_id) DO UPDATE SET expires_at = excluded.expires_at
                """;

        try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
            deleteStatement.setString(1, chestId.toString());
            deleteStatement.executeUpdate();
        }

        try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
            for (Map.Entry<UUID, Instant> entry : cooldowns.entrySet()) {
                if (entry.getValue() == null) {
                    continue;
                }

                insertStatement.setString(1, chestId.toString());
                insertStatement.setString(2, entry.getKey().toString());
                insertStatement.setLong(3, entry.getValue().toEpochMilli());
                insertStatement.addBatch();
            }

            insertStatement.executeBatch();
        }
    }

    private void removeCooldowns(Connection connection, UUID chestId) throws SQLException {
        String sql = "DELETE FROM chest_cooldowns WHERE chest_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, chestId.toString());
            statement.executeUpdate();
        }
    }

    @Override
    public void remove(ChestModel chestModel) {
        String sql = "DELETE FROM chests WHERE chest_id = ?";

        try (Connection connection = this.database.getConnection()) {
            connection.setAutoCommit(false);
            removeCooldowns(connection, chestModel.chestId());
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, chestModel.chestId().toString());
                statement.executeUpdate();
            }
            connection.commit();
        } catch (SQLException exception) {
            throw new IllegalStateException("Could not remove chest " + chestModel.chestId(), exception);
        }
    }

    @Override
    public void update(ChestModel chestModel) {
        save(chestModel);
    }

    @Override
    public Optional<ChestModel> find(UUID chestId) {
        String sql = "SELECT * FROM chests WHERE chest_id = ?";

        try (Connection connection = this.database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, chestId.toString());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }

                return Optional.of(readChest(connection, resultSet));
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("Could not find chest " + chestId, exception);
        }
    }

    @Override
    public Optional<ChestModel> find(int x, int y, int z, UUID worldId) {
        String sql = "SELECT * FROM chests WHERE world_id = ? AND x = ? AND y = ? AND z = ?";

        try (Connection connection = this.database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, worldId.toString());
            statement.setInt(2, x);
            statement.setInt(3, y);
            statement.setInt(4, z);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }

                return Optional.of(readChest(connection, resultSet));
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("Could not find chest at " + worldId + ":" + x + "," + y + "," + z, exception);
        }
    }

    @Override
    public List<ChestModel> findAll() {
        String sql = "SELECT * FROM chests";
        List<ChestModel> chests = new ArrayList<>();

        try (Connection connection = this.database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                chests.add(readChest(connection, resultSet));
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("Could not load chests", exception);
        }

        return chests;
    }

    private void fillChestStatement(PreparedStatement statement, ChestModel chestModel) throws SQLException {
        statement.setString(1, chestModel.chestId().toString());
        statement.setString(2, chestModel.worldId().toString());
        statement.setInt(3, chestModel.x());
        statement.setInt(4, chestModel.y());
        statement.setInt(5, chestModel.z());
    }

    private ChestModel readChest(Connection connection, ResultSet resultSet) throws SQLException {
        UUID chestId = UUID.fromString(resultSet.getString("chest_id"));

        return new ChestModel(
                chestId,
                resultSet.getInt("x"),
                resultSet.getInt("y"),
                resultSet.getInt("z"),
                UUID.fromString(resultSet.getString("world_id")),
                loadCooldowns(connection, chestId)
        );
    }

    private Map<UUID, Instant> loadCooldowns(Connection connection, UUID chestId) throws SQLException {
        String sql = "SELECT player_id, expires_at FROM chest_cooldowns WHERE chest_id = ?";
        Map<UUID, Instant> cooldowns = new HashMap<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, chestId.toString());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    cooldowns.put(
                            UUID.fromString(resultSet.getString("player_id")),
                            Instant.ofEpochMilli(resultSet.getLong("expires_at"))
                    );
                }
            }
        }

        return cooldowns;
    }


}
