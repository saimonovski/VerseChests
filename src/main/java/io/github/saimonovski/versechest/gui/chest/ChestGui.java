package io.github.saimonovski.versechest.gui.chest;

import io.github.saimonovski.versechest.chest.Chest;
import io.github.saimonovski.versechest.chest.item.ChestItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;


import java.util.List;

public class ChestGui implements InventoryHolder {
    private final Inventory inventory;
    private final List<ChestItem> chestItems;
    private final Chest chest;

    public ChestGui(List<ChestItem> chestItems, Component title, int size, Chest chest) {
        this.chestItems = chestItems;
        this.inventory = Bukkit.createInventory(this,size,title);
        insertItems();
        this.chest = chest;
    }
    private void insertItems(){

    }


    /**
     * Get the object's inventory.
     *
     * @return The inventory.
     */
    @Override
    public @NotNull Inventory getInventory() {
        return null;
    }

    public Chest getChest() {
        return this.chest;
    }
}
