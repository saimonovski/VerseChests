package io.github.saimonovski.versechest.gui.api.listener;

import io.github.saimonovski.versechest.gui.api.Gui;
import io.github.saimonovski.versechest.gui.api.GuiManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class InventoryListener implements Listener {


    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        Inventory inventory   = e.getClickedInventory();
        assert  inventory != null;
        InventoryHolder inventoryHolder = inventory.getHolder(false);
        if(!(inventoryHolder instanceof Gui gui)) {
            return;
        }
        gui.clickAction(e);
    }
    @EventHandler
    public void onInventoryCLose(InventoryCloseEvent e){
        Inventory inventory   = e.getInventory();
        InventoryHolder inventoryHolder = inventory.getHolder(false);
        if(!(inventoryHolder instanceof Gui gui)) {
            return;
        }
        gui.inventoryCloseAction(e);
    }
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e){
        Inventory inventory   = e.getInventory();
        InventoryHolder inventoryHolder = inventory.getHolder(false);
        if(!(inventoryHolder instanceof Gui gui)) {
            return;
        }
        gui.inventoryOpenAction(e);
    }
}
