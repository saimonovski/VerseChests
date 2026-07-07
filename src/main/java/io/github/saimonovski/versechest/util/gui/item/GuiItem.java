package io.github.saimonovski.versechest.util.gui.item;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface GuiItem {
    ItemStack getItemStack();
    int slot();
    void setItemStack(ItemStack itemStack);
    void onClick(InventoryClickEvent e);
}
