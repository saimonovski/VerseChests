package io.github.saimonovski.versechest.gui.api.item;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public interface GuiItem {
    ItemStack getItemStack();
    int slot();
    ItemStack setItemStack();
    Consumer<InventoryClickEvent> getClickAction();

}
