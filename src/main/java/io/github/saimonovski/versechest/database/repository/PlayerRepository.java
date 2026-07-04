package io.github.saimonovski.versechest.database.repository;

import java.util.Map;
import java.util.UUID;

public interface PlayerRepository {
    int findPlayerCount(UUID playerId);

    Map<UUID, Integer> findAllPlayerCounts();

    void updatePlayerCount(UUID playerId, int playerCount);

    void removePlayerCount(UUID playerId);
}
