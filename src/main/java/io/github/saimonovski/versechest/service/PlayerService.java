package io.github.saimonovski.versechest.service;

import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerService {
    private final Map<UUID, Integer> playerOpenedChestsCount = new HashMap<>();


    public  int getOpenedChestCount(UUID id){
        var openedChests = this.playerOpenedChestsCount.get(id);
        return openedChests == null ? 0 : openedChests;
    }
    public void increaseCount(UUID id){
        this.playerOpenedChestsCount.compute(id, (k, current) -> current != null ? current + 1 : 1);
    }
    public void setPlayerOpenedChestsCount(UUID id, int count){
        this.playerOpenedChestsCount.put(id,count);
    }

    public @NotNull String getFormattedCooldownTime(Player player, Chest chest) {
        return null;
    }
}
