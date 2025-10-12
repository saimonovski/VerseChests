package io.github.saimonovski.versechest.chestPlayer;

import io.github.saimonovski.versechest.chest.Chest;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChestPlayer {
    UUID playerUUID;
    int chestOpenedCount;
    Map<Chest, LocalDateTime> chestCooldowns;

    public ChestPlayer(UUID playerUUID, int chestOpenedCount){
        this.playerUUID = playerUUID;
        this.chestOpenedCount = chestOpenedCount;
        this.chestCooldowns = new HashMap<>();
    }
    public void setPlayerToCooldown(int cooldownTime, Chest chest){
        this.chestCooldowns.put(chest,LocalDateTime.now().plusSeconds(cooldownTime));
    }

    public boolean isPlayerOnCooldown(Chest chest) {
        LocalDateTime localDateTime = this.chestCooldowns.get(chest);
        if(localDateTime == null) return false;
        boolean isStillOnCooldown = isStillCooldown(localDateTime);
        if(isStillOnCooldown){
            return true;
        }
        this.chestCooldowns.remove(chest);
        return false;
    }
    private boolean isStillCooldown(LocalDateTime cooldownEndDateTime){
        LocalDateTime now = LocalDateTime.now();
        Duration between = Duration.between(now,cooldownEndDateTime);
        return between.getSeconds() <= 0;
    }

    public void addPlayerOpenedChest() {
        this.chestOpenedCount++;
    }

    public int getOpenedChests() {
        return this.chestOpenedCount;
    }
}
