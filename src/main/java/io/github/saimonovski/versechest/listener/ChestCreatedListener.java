package io.github.saimonovski.versechest.listener;

import io.github.saimonovski.versechest.chest.service.ChestService;
import io.github.saimonovski.versechest.config.MainConfig;
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
    private final MainConfig.ChestConfiguration chestConfiguration;
    private final MainConfig mainConfig;

    public ChestCreatedListener(ChestService chestService, MainConfig mainConfig) {
        this.chestService = chestService;
        this.blockCreator = mainConfig.getBlockCreator();
        this.chestConfiguration = mainConfig.getChestConfiguration();
        this.mainConfig = mainConfig;
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
