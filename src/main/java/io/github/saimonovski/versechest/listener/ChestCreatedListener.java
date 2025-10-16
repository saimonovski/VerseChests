package io.github.saimonovski.versechest.listener;

import com.google.inject.Inject;
import io.github.saimonovski.versechest.chest.service.ChestService;
import io.github.saimonovski.versechest.config.MainConfig;
import io.github.saimonovski.versechest.config.service.ConfigService;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class ChestCreatedListener implements Listener {
    private final ChestService chestService;
    private final MainConfig.BlockCreator blockCreator;

    private final MainConfig mainConfig;

    @Inject
    public ChestCreatedListener(ChestService chestService, ConfigService configService) {
        this.chestService = chestService;
        this.mainConfig = configService.getMainConfig();
        this.blockCreator = this.mainConfig.getBlockCreator();

    }
    @EventHandler
    public void onPlayerPlaceBlock(BlockPlaceEvent e){
        ItemStack blockCreatorItemStack =  blockCreator.getItemStack();
        ItemStack itemstack = e.getItemInHand();
        Location location = e.getBlock().getLocation();
        if(!blockCreatorItemStack.isSimilar(itemstack)) return;
        this.chestService.registerChest(location,this.mainConfig);
        //todo send a message that chest has been created
    }
}
