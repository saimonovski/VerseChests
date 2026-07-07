package io.github.saimonovski.versechest.test;

import io.github.saimonovski.versechest.VerseChest;
import io.github.saimonovski.versechest.test.gui.PlayerJoinListener;

public class GuiTest {
    public static  VerseChest plugin;

    public GuiTest(VerseChest plugin) {
        GuiTest.plugin = plugin;
    }
    public boolean prepare(){
        plugin.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), plugin);
        return true;
    }
}
