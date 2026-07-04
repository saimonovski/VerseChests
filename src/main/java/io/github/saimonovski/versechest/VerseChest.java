package io.github.saimonovski.versechest;

import io.github.saimonovski.versechest.database.repository.ChestRepository;
import io.github.saimonovski.versechest.database.Database;
import io.github.saimonovski.versechest.database.HikariDatabase;
import io.github.saimonovski.versechest.database.repository.PlayerRepository;
import io.github.saimonovski.versechest.database.repository.implementation.SqlChestRepository;
import io.github.saimonovski.versechest.database.repository.implementation.SqlPlayerRepository;
import io.github.saimonovski.versechest.listener.ChestListener;
import io.github.saimonovski.versechest.service.ChestService;
import io.github.saimonovski.versechest.service.ConfigService;
import io.github.saimonovski.versechest.service.PlayerService;
import io.github.saimonovski.versechest.util.ChatUtil;
import io.github.saimonovski.versechest.util.Logger;
import io.github.saimonovski.versechest.util.message.Message;
import io.github.saimonovski.versechest.util.message.MessageType;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;


public final class VerseChest extends JavaPlugin {
    private Logger logger;
    private Metrics metrics;
    private Database database;
    private ChestRepository chestRepository;
    private PlayerRepository playerRepository;
    private ConfigService configService;
    private ChestService chestService;
    private PlayerService playerService;
    private static final int PLUGIN_ID = 27630;
    public static String ADMIN_PERMISSION = "smv.versechests.admin";
    public static Message NO_PERMISSION_MESSAGE = new Message("<red> You don't have permission for this.", MessageType.CHAT);

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
        this.logger = new Logger(this, false);
        this.logger.info("Version: " + getPluginMeta().getVersion());
        this.logger.info("loading Bstats");
        metrics = new Metrics(this, PLUGIN_ID);
        this.logger.info("Bstats loaded");
        loadServices();
        loadListeners();
        this.logger.info("Plugin loaded, if you met any issues report them by  discord private message: https://discord.com/users/1022368581436047420");
    }

    private void loadListeners() {
        this.logger.debug("loading listener");
        this.getServer().getPluginManager().registerEvents(new ChestListener(this.playerService, this.chestService, this.logger, this.configService), this);
    }

    private void loadServices() {
        this.logger.debug("loading services");

        this.configService = new ConfigService(this);
        this.logger.setDebugEnabled(getConfig().getBoolean("debug-enabled", false));

        this.database = new HikariDatabase(this, this.logger);
        this.database.connect();
        this.database.createTables();

        this.chestRepository = new SqlChestRepository(this.database);
        this.playerRepository = new SqlPlayerRepository(this.database);
        this.playerService = new PlayerService(this.playerRepository);
        this.chestService = new ChestService(this.chestRepository, this.configService, this.logger);
        this.chestService.loadChests();
    }

    @Override
    public void onDisable() {
        if (this.metrics != null) {
            this.metrics.shutdown();
        }

        if (this.database != null) {
            this.database.disconnect();
        }
    }

    @NotNull
    public Logger logger() {
        return logger;
    }

}
