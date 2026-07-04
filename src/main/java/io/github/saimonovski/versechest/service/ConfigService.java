package io.github.saimonovski.versechest.service;

import io.github.saimonovski.versechest.config.ChestConfig;
import io.github.saimonovski.versechest.config.MessageConfig;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigService {
    private final JavaPlugin plugin;
    private ChestConfig chestConfig;
    private MessageConfig messageConfig;

    public ConfigService(JavaPlugin plugin) {
        this.plugin = plugin;
        this.plugin.saveDefaultConfig();
        reload();
    }

    public void reload() {
        this.plugin.reloadConfig();

        Material chestMaterial = Material.matchMaterial(this.plugin.getConfig().getString("chestConfiguration.material", "CHEST"));
        if (chestMaterial == null) {
            chestMaterial = Material.CHEST;
        }

        int cooldownDuration = this.plugin.getConfig().getInt("chestCooldown", 3600);
        ChestCooldownService.TimeUnit cooldownUnit = readCooldownUnit();

        this.chestConfig = new ChestConfig(chestMaterial, cooldownDuration, cooldownUnit);
        this.messageConfig = new MessageConfig();
    }

    public ChestConfig getChestConfig() {
        return this.chestConfig;
    }

    public MessageConfig getMessageConfig() {
        return this.messageConfig;
    }

    private ChestCooldownService.TimeUnit readCooldownUnit() {
        String configuredUnit = this.plugin.getConfig().getString("chestCooldownUnit", "SECOND");

        try {
            return ChestCooldownService.TimeUnit.valueOf(configuredUnit.toUpperCase());
        } catch (IllegalArgumentException exception) {
            return ChestCooldownService.TimeUnit.SECOND;
        }
    }
}
