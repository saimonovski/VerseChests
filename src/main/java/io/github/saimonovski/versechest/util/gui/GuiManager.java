package io.github.saimonovski.versechest.util.gui;

import io.github.saimonovski.versechest.util.gui.listener.InventoryListener;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GuiManager {

    public void registerManager(JavaPlugin javaPlugin){

        javaPlugin.getServer().getPluginManager().registerEvents(new InventoryListener(),javaPlugin);
    }
}
