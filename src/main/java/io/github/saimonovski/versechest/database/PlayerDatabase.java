package io.github.saimonovski.versechest.database;

import java.util.UUID;

public interface PlayerDatabase extends Database {
    int findPlayerCount(UUID playerId);

    void updatePlayerCount(UUID playerId, int playerCount);

    void removePlayerCount(UUID playerId);
}
