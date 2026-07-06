package io.github.saimonovski.versechest.gui.api;

import io.github.saimonovski.versechest.gui.api.item.GuiItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;

public interface Gui extends InventoryHolder {
    void setItem(int slot, GuiItem guiItem);

    void open(Player player);

    void open(Player ...players);

    Background background();
    
    void clickAction(InventoryClickEvent e);

    void inventoryCloseAction(InventoryCloseEvent e);

    void inventoryOpenAction(InventoryOpenEvent e);

    int size();
}
