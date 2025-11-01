package io.github.saimonovski.versechest.paper.util;

import io.github.saimonovski.versechest.paper.VerseChest;

public class Logger {
    private final VerseChest plugin;
    private  boolean debugEnabled;

    public Logger(VerseChest plugin, boolean debugEnabled) {
        this.plugin = plugin;
        this.debugEnabled = debugEnabled;
    }
    public void debug(String message){
        if(debugEnabled) {
            plugin.getLogger().info("[DEBUG] " + message);
        }
    }
    public void info(String message){
        plugin.getLogger().info(message);
    }
    public void warning(String message){
        plugin.getLogger().warning(message);
    }
    public void error(String message){
        plugin.getLogger().severe(message);
    }

    public void setDebugEnabled(boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
    }
}
