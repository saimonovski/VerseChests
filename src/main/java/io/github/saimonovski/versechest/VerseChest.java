package io.github.saimonovski.versechest;

import io.github.saimonovski.versechest.service.ChestCooldownService;
import io.github.saimonovski.versechest.util.ChatUtil;
import io.github.saimonovski.versechest.util.Logger;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;


public final class VerseChest extends JavaPlugin {
    private Logger logger;

    private Metrics metrics;
    private static final int PLUGIN_ID = 27630;
    @Override
    public void onEnable() {

        String text = """
                           <b><gradient:#FF8600:#FFCF00>
                           \n
                            ____   ____                           _________ .__                     __
                            \\   \\ /   /___________  ______ ____   \\_   ___ \\|  |__   ____   _______/  |_
                             \\   Y   // __ \\_  __ \\/  ___// __ \\  /    \\  \\/|  |  \\_/ __ \\ /  ___/\\   __\\
                              \\     /\\  ___/|  | \\/\\___ \\\\  ___/  \\     \\___|   Y  \\  ___/ \\___ \\  |  |
                               \\___/  \\___  >__|  /____  >\\___  >  \\______  /___|  /\\___  >____  > |__|
                                          \\/           \\/     \\/          \\/     \\/     \\/     \\/
                                         \s
                                         \n
                                         </gradient></b>
                                         <gradient:#FFCF00:#FF8600>by saimonovski </gradient>
               \s""";
        Bukkit.getConsoleSender().sendMessage(ChatUtil.fix(text));
        this.logger = new Logger(this,false);
        this.logger.info("Version: "+getPluginMeta().getVersion());
        this.logger.info("loading Bstats");
         metrics = new Metrics(this,PLUGIN_ID);
        this.logger.info("Bstats loaded");
        loadServices();
        this.logger.info("Plugin loaded, if you met any issues report them by  discord private message: https://discord.com/users/1022368581436047420");
    }

    private void loadServices() {
    }

    @Override
    public void onDisable() {
        this.metrics.shutdown();
    }

    @NotNull
    public Logger logger() {
        return logger;
    }




}
