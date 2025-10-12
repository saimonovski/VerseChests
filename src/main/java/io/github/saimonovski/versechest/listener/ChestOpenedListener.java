package io.github.saimonovski.versechest.listener;

import io.github.saimonovski.versechest.chest.Chest;
import io.github.saimonovski.versechest.chest.item.ChestItem;
import io.github.saimonovski.versechest.chest.service.ChestService;
import io.github.saimonovski.versechest.gui.chest.ChestGui;
import io.github.saimonovski.versechest.chest.item.service.ChestItemService;
import io.github.saimonovski.versechest.chestPlayer.service.PlayerService;
import io.github.saimonovski.versechest.chest.rarity.service.RarityService;
import io.github.saimonovski.versechest.util.ChatUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import javax.annotation.Nullable;
import java.util.*;

public class ChestOpenedListener implements Listener {
    private final ChestService chestService;
    private final RarityService rarityService;
    private final ChestItemService chestItemService;
    private final PlayerService playerService;

    private Map<UUID,Chest> chestMap = new HashMap<>();

    public ChestOpenedListener(ChestService chestService, RarityService rarityService, ChestItemService chestItemService, PlayerService playerService) {
        this.chestService = chestService;
        this.rarityService = rarityService;
        this.chestItemService = chestItemService;
        this.playerService = playerService;
    }

    @Nullable
    private Chest getChestFromBlock(Block block){
           return this.chestService.getChestFromLocation(block.getLocation());
    }

    private void openChestGui(Chest chest,  Player player){
        var config = chest.config().getChestConfiguration();
        int chestGuiSize = config.getSize();
        int maxItems = config.getMaxItems();
        int minItems = config.getMinItems();
        Component title = ChatUtil.fix(config.getTitle());
        int totalItemsAmount = new Random().nextInt(minItems,maxItems);
        Set<ChestItem> randomizeChestItems = this.chestItemService.randomizeChestItems(this.rarityService.getRarityValues(), totalItemsAmount);
        ChestGui chestGui = new ChestGui(randomizeChestItems.stream().toList(),title,chestGuiSize, chest);
        player.openInventory(chestGui.getInventory());
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerOpenChest(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        if(block == null) return;
        Chest chest = getChestFromBlock(block);
        if(chest == null) return;
        event.setCancelled(true);
        if(this.playerService.isPlayerOnCooldown(player, chest)){
            //todo send a cooldown message
            return;
        }
        openChestGui(chest, player);
    }

    @EventHandler
    public void onPlayerCloseChest(InventoryCloseEvent e){
        Inventory inventory = e.getInventory();
        Player player = (Player) e.getPlayer();
        InventoryHolder inventoryHolder = inventory.getHolder();
        if(!(inventoryHolder instanceof ChestGui gui)) return;

        this.playerService.setPlayerToCooldown(player,gui.getChest());
        this.playerService.addPlayerOpenedChest(player);
    }
}
