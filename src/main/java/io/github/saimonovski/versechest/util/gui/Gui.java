package io.github.saimonovski.versechest.util.gui;

import io.github.saimonovski.versechest.util.gui.item.GuiItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;

import javax.annotation.Nullable;

public interface Gui extends InventoryHolder {
    void setItem(int slot, GuiItem guiItem);

    void open(Player player);

    void open(Player ...players);
    @Nullable
    Background background();
    
    void clickAction(InventoryClickEvent e);

    void inventoryCloseAction(InventoryCloseEvent e);

    void inventoryOpenAction(InventoryOpenEvent e);

    int size();
}
