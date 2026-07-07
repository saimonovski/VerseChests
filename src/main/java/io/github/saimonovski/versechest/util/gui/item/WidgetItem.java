package io.github.saimonovski.versechest.util.gui.item;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class WidgetItem implements GuiItem{
    private ItemStack itemStack;
    @Override
    public ItemStack getItemStack() {
        return this.itemStack;
    }

    @Override
    public int slot() {
        return 0;
    }

    @Override
    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }


    public void onClick(InventoryClickEvent e){
        //ignore
        return;
    }

}
