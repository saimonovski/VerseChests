package io.github.saimonovski.versechest.paper.listener;

import com.google.inject.Inject;
import io.github.saimonovski.versechest.common.application.service.impl.ChestService;
import io.github.saimonovski.versechest.paper.config.MainConfig;
import io.github.saimonovski.versechest.paper.config.MessageConfig;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class ChestCreatedListener implements Listener {
    private final ChestService chestService;
    private final MainConfig.BlockCreator blockCreator;
    private final MessageConfig messageConfig;
    private final MainConfig mainConfig;

    @Inject
    public ChestCreatedListener(ChestService chestService, MainConfig mainConfig, MessageConfig messages) {
        this.chestService = chestService;
        this.mainConfig = mainConfig;
        this.blockCreator = this.mainConfig.getBlockCreator();
        this.messageConfig = messages;
    }
    @EventHandler
    public void onPlayerPlaceBlock(BlockPlaceEvent e){
        ItemStack blockCreatorItemStack =  blockCreator.getItemStack();
        ItemStack itemstack = e.getItemInHand();
        Location location = e.getBlock().getLocation();
        if(!blockCreatorItemStack.isSimilar(itemstack)) return;
        this.chestService.registerChest(location,this.mainConfig);
        this.messageConfig.chestCreatedMessage().send(e.getPlayer());

    }
}
