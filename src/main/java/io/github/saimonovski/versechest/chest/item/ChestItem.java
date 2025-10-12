package io.github.saimonovski.versechest.chest.item;

import io.github.saimonovski.versechest.chest.rarity.Rarity;
import org.bukkit.inventory.ItemStack;

public class ChestItem {
    private final ItemStack itemStack;
    private final Rarity dropRarity;
    int dropChance = -1;


    public ChestItem(ItemStack itemStack, Rarity dropRarity) {
        this.itemStack = itemStack;
        this.dropRarity = dropRarity;
    }
    public void setDropChance(int dropChance){
        this.dropChance = dropChance;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Rarity getDropRarity() {
        return dropRarity;
    }

    public int getDropChance() {
        return dropChance;
    }
}
