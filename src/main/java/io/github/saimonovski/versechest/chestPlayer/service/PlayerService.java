package io.github.saimonovski.versechest.chestPlayer.service;

import io.github.saimonovski.versechest.chest.Chest;
import io.github.saimonovski.versechest.chestPlayer.ChestPlayer;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerService {
    private final Map<UUID, ChestPlayer> chestPlayers = new HashMap<>();
//    private final Map<UUID, LocalDateTime> cooldownPlayers = new HashMap<>();
//    private final Map<UUID,Integer> chestOpenedPlayers = new HashMap<>();
    private final int cooldownTime;

    public PlayerService(int cooldownTime) {
        this.cooldownTime = cooldownTime;
    }

    public void setPlayerToCooldown(Player player, Chest chest){
        this.chestPlayers.get(player.getUniqueId()).setPlayerToCooldown(this.cooldownTime,chest);
    }
    public boolean isPlayerOnCooldown(Player player, Chest chest){
        return this.chestPlayers.get(player.getUniqueId()).isPlayerOnCooldown(chest);
    }
    public boolean isPlayerOnCooldown(UUID uuid, Chest chest){
        ChestPlayer chestPlayer = this.chestPlayers.get(uuid);
        return chestPlayer.isPlayerOnCooldown(chest);
    }
    public void addPlayerOpenedChest(Player player){
        this.chestPlayers.get(player.getUniqueId()).addPlayerOpenedChest();
    }
    public int getPlayerOpenedChests(Player player){
        return this.chestPlayers.get(player.getUniqueId()).getOpenedChests();
    }
    public String getFormattedCooldownTime(Player player, Chest chest){
        ChestPlayer chestPlayer = this.chestPlayers.get(player.getUniqueId());
        LocalDateTime cooldownEndDateTime = chestPlayer.getCooldownEndDate(chest);
        if(cooldownEndDateTime == null) return "";
        LocalDateTime now = LocalDateTime.now();
        Duration between = Duration.between(cooldownEndDateTime,now);
        long seconds = between.toSeconds();
        long minutes = between.toMinutes();
        long hours = between.toHours();

        StringBuilder timerString = new StringBuilder();
        if(hours > 0) timerString.append(hours).append("h ");
        if(minutes > 0) timerString.append(minutes).append("min ");
        if(seconds > 0) timerString.append(hours).append("s ");

        return timerString.toString();
    }
}
