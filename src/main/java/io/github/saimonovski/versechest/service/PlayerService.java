package io.github.saimonovski.versechest.service;

import io.github.saimonovski.versechest.database.repository.PlayerRepository;
import io.github.saimonovski.versechest.entity.ChestEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerService {
    private final PlayerRepository playerRepository;
    private final Map<UUID, Integer> playerOpenedChestsCount = new HashMap<>();

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
        this.playerOpenedChestsCount.putAll(playerRepository.findAllPlayerCounts());
    }

    public int getOpenedChestCount(UUID id) {
        var openedChests = this.playerOpenedChestsCount.get(id);
        if (openedChests != null) {
            return openedChests;
        }

        int count = this.playerRepository.findPlayerCount(id);
        this.playerOpenedChestsCount.put(id, count);
        return count;
    }

    public void increaseCount(UUID id) {
        int count = getOpenedChestCount(id) + 1;
        setPlayerOpenedChestsCount(id, count);
    }

    public void setPlayerOpenedChestsCount(UUID id, int count) {
        int safeCount = Math.max(count, 0);
        this.playerOpenedChestsCount.put(id, safeCount);
        this.playerRepository.updatePlayerCount(id, safeCount);
    }

    public void removePlayerOpenedChestsCount(UUID id) {
        this.playerOpenedChestsCount.remove(id);
        this.playerRepository.removePlayerCount(id);
    }

    public @NotNull String getFormattedCooldownTime(Player player, ChestEntity chest) {
        return "";
    }//todo
}
