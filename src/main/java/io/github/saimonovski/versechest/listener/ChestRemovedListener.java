package io.github.saimonovski.versechest.listener;

import com.google.inject.Inject;
import io.github.saimonovski.versechest.chest.Chest;
import io.github.saimonovski.versechest.chest.service.ChestService;
import io.github.saimonovski.versechest.config.ConfigPaths;
import io.github.saimonovski.versechest.config.MainConfig;
import io.papermc.paper.event.block.BlockBreakBlockEvent;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class ChestRemovedListener implements Listener {
    private final ChestService chestService;

    @Inject
    public ChestRemovedListener(ChestService chestService) {
        this.chestService = chestService;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        Location location = e.getBlock().getLocation();
        if(!this.chestService.isChestExist(location)) return;
        if(e.getPlayer().isSneaking() && e.getPlayer().hasPermission(MainConfig.ADMIN_PERMISSION)){
            this.chestService.removeChest(location);
            return;
        }
        e.setCancelled(true);
    }
}
