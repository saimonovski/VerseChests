package io.github.saimonovski.versechest.message.replacers;

import io.github.saimonovski.versechest.chest.Chest;
import io.github.saimonovski.versechest.chestPlayer.service.PlayerService;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.entity.Player;

public class Replacer {
    public static TextReplacementConfig replacePlayer(Player player){
        return TextReplacementConfig.builder()
                .match("%player%")
                .replacement(player.getName())
                .build();
    }
    public static TextReplacementConfig replacePlayer(String player){
        return TextReplacementConfig.builder()
                .match("%player%")
                .replacement(player)
                .build();
    }
    public static TextReplacementConfig replaceAmount(double amount){
        return TextReplacementConfig.builder()
                .match("%amount%")
                .replacement(amount+"")
                .build();
    }
    public static TextReplacementConfig replacePlayerTime(Player player, PlayerService playerService, Chest chest){
        return TextReplacementConfig.builder()
                .match("%time%")
                .replacement(playerService.getFormattedCooldownTime(player, chest))
                .build();
    }
}
