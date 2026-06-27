package io.github.saimonovski.versechest.config;

import io.github.saimonovski.versechest.service.ChestCooldownService;
import org.bukkit.Material;

public class ChestConfig {
    private Material chestMaterial;
    private int chestCooldownDuration;
    private ChestCooldownService.TimeUnit chestCooldownUnit;

    public Material chestMaterial() {
        return chestMaterial;
    }

    public int chestCooldownDuration() {
        return chestCooldownDuration;
    }

    public ChestCooldownService.TimeUnit chestCooldownUnit() {
        return chestCooldownUnit;
    }
}
