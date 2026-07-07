package io.github.saimonovski.versechest.test.gui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent e){
        new PaginatedGuiStorage().open(e.getPlayer());
    }
}
