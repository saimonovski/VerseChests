package io.github.saimonovski.versechest.listener;

import io.github.saimonovski.versechest.VerseChest;
import io.github.saimonovski.versechest.config.MessageConfig;
import io.github.saimonovski.versechest.entity.ChestEntity;
import io.github.saimonovski.versechest.service.ChestService;
import io.github.saimonovski.versechest.service.ConfigService;
import io.github.saimonovski.versechest.service.PlayerService;
import io.github.saimonovski.versechest.util.Logger;
import io.github.saimonovski.versechest.util.message.Message;
import io.github.saimonovski.versechest.util.message.MessageType;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ChestListener implements Listener {
    private final PlayerService playerService;
    private final ChestService chestService;
    private final Logger logger;
    private final ConfigService configService;

    public ChestListener(PlayerService playerService, ChestService chestService, Logger logger, ConfigService configService) {
        this.playerService = playerService;
        this.chestService = chestService;
        this.logger = logger;
        this.configService = configService;
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
            MessageConfig messageConfig = configService.getMessageConfig();
            Message onCooldownMessage = messageConfig.playerOnCooldownMessage();
            onCooldownMessage.replace(Map.of(
                    "%player%",player.getName(),
                    "%time%", cooldownTime
            ));
            onCooldownMessage.send(player);
            return;
        }

        chestService.openChest(player, chestEntity);
        playerService.increaseCount(player.getUniqueId());
        Message chestLooted = this.configService.getMessageConfig().getChestLootedMessage();
        chestLooted.replace("%player%",player.getName()).send(player);

    }

    @EventHandler
    private void onPlayerDestroyChest(BlockBreakEvent blockBreakEvent) {
        Location location = blockBreakEvent.getBlock().getLocation();
        ChestEntity chestEntity = this.chestService.findChest(location);
        Player player = blockBreakEvent.getPlayer();
        if (chestEntity == null) {
            return;
        }
        if(!player.isOp() || !player.hasPermission(VerseChest.ADMIN_PERMISSION)){
            blockBreakEvent.setCancelled(true);
            return;
        }
        if(!player.isSneaking()){
            new Message("<red> You need to crouching to destroy a chest.", MessageType.CHAT);
        }
        
        this.chestService.removeChest(chestEntity);
        this.logger.info("Chest from: [x,y,z] "+location.getBlockX()+location.getBlockY()+location.getBlockZ()+"has been removed by "+player.getName());
        Message chestRemovedMessage = new Message("<green> chest removed successfully", MessageType.CHAT);
        chestRemovedMessage.send(player);
    }

    @EventHandler
    private void onPlayerPlaceChestCreator(BlockPlaceEvent event) {
        ItemStack itemToPlace = event.getItemInHand();
        ItemStack creator = this.configService.getChestConfig().getItemCreator();
        if(!creator.isSimilar(itemToPlace)) return;
        Location location = event.getBlock().getLocation();
        Player player = event.getPlayer();
        if(!player.isOp() || !player.hasPermission(VerseChest.ADMIN_PERMISSION)) {
            VerseChest.NO_PERMISSION_MESSAGE.send(player);
            return;
        }
        ChestEntity chestEntity = chestService.createChest(location);
        chestEntity.updateBLock();
        this.logger.debug("chest created by "+player.getName());
        Message chestCreatedMessage = new Message("<green> Chest has been created.", MessageType.CHAT);
        chestCreatedMessage.send(player);
    }


}
