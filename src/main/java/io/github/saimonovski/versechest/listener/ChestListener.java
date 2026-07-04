package io.github.saimonovski.versechest.listener;

import io.github.saimonovski.versechest.entity.ChestEntity;
import io.github.saimonovski.versechest.service.ChestService;
import io.github.saimonovski.versechest.service.PlayerService;
import io.github.saimonovski.versechest.util.Logger;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ChestListener implements Listener {
    private final PlayerService playerService;
    private final ChestService chestService;
    private final Logger logger;

    public ChestListener(PlayerService playerService, ChestService chestService, Logger logger) {
        this.playerService = playerService;
        this.chestService = chestService;
        this.logger = logger;
    }

    @EventHandler
    private void onPlayerOpenChest(PlayerInteractEvent event) {
        if (!event.getAction().isRightClick()) {
            return;
        }

        Block clickedBlock = event.getClickedBlock();

        if (clickedBlock == null || clickedBlock.isLiquid() || clickedBlock.getType().isAir()) {
            return;
        }
        Location location = clickedBlock.getLocation();

        ChestEntity chestEntity = this.chestService.findChest(location);

        if (chestEntity == null) {
            return;
        }

        Player player = event.getPlayer();

        this.logger.debug("Chest opened, id: " + chestEntity.getChestId());
        this.logger.debug("By: " + player.getName());

        if (chestEntity.getChestCooldownService().isStillOnCooldown(player.getUniqueId())) {
            this.logger.debug("player on cooldown, action disabled");
            String cooldownTime = playerService.getFormattedCooldownTime(player,chestEntity);

            return;
        }

        chestService.openChest(player, chestEntity);
        playerService.increaseCount(player.getUniqueId());

    }

    @EventHandler
    private void onPlayerDestroyChest(BlockBreakEvent blockBreakEvent) {
        Location location = blockBreakEvent.getBlock().getLocation();
        ChestEntity chestEntity = this.chestService.findChest(location);
        Player player = blockBreakEvent.getPlayer();
        //todo check if player has required permissions
        //todo check if player is sneaking
        //todo if all above is correct  remove chest
        if (chestEntity == null) {
            return;
        }

        this.chestService.removeChest(chestEntity);
    }

    @EventHandler
    private void onPlayerPlaceChestCreator(BlockPlaceEvent event) {
        ItemStack itemToPlace = event.getItemInHand();
        //todo check if the item is the same as defined in config for block creator
        Location location = event.getBlock().getLocation();
        //todo create a chest
        ChestEntity chestEntity = chestService.createChest(location);
        chestEntity.updateBLock();


        // chestEntity.updateBLock();
        //todo send a message
    }


}
