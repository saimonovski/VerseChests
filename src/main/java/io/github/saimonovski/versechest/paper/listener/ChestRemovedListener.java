package io.github.saimonovski.versechest.paper.listener;

import com.google.inject.Inject;
import io.github.saimonovski.versechest.common.application.service.impl.ChestService;
import io.github.saimonovski.versechest.paper.config.MainConfig;
import io.github.saimonovski.versechest.paper.config.MessageConfig;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class ChestRemovedListener implements Listener {
    private final ChestService chestService;
    private final MessageConfig messageConfig;
    @Inject
    public ChestRemovedListener(ChestService chestService, MessageConfig messageConfig) {
        this.chestService = chestService;
        this.messageConfig = messageConfig;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        Location location = e.getBlock().getLocation();
        if(!this.chestService.isChestExist(location)) return;
        if(e.getPlayer().isSneaking() && e.getPlayer().hasPermission(MainConfig.ADMIN_PERMISSION)){
            this.chestService.removeChest(location);
            this.messageConfig.chestRemovedMessage().send(e.getPlayer());
            return;
        }
        e.setCancelled(true);

    }
}
