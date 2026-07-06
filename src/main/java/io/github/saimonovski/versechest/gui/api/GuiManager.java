package io.github.saimonovski.versechest.gui.api;

import io.github.saimonovski.versechest.gui.api.listener.InventoryListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class GuiManager {

    public void registerManager(JavaPlugin javaPlugin){
        javaPlugin.getServer().getPluginManager().registerEvents(new InventoryListener(),javaPlugin);
    }
}
