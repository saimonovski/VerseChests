package io.github.saimonovski.versechest.config;

import io.github.saimonovski.versechest.service.ChestCooldownService;
import io.github.saimonovski.versechest.util.message.Message;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ChestConfig {
    private final Material chestMaterial;
    private final int chestCooldownDuration;
    private final ChestCooldownService.TimeUnit chestCooldownUnit;

    public ChestConfig(Material chestMaterial, int chestCooldownDuration, ChestCooldownService.TimeUnit chestCooldownUnit) {
        this.chestMaterial = chestMaterial;
        this.chestCooldownDuration = chestCooldownDuration;
        this.chestCooldownUnit = chestCooldownUnit;
    }

    public Material chestMaterial() {
        return chestMaterial;
    }

    public int chestCooldownDuration() {
        return chestCooldownDuration;
    }

    public ChestCooldownService.TimeUnit chestCooldownUnit() {
        return chestCooldownUnit;
    }


    public ItemStack getItemCreator() {
        //todo
    }
}
