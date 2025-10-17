package io.github.saimonovski.versechest;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.github.saimonovski.versechest.chest.item.service.ChestItemService;
import io.github.saimonovski.versechest.chest.rarity.service.RarityService;
import io.github.saimonovski.versechest.chest.service.ChestService;
import io.github.saimonovski.versechest.chestPlayer.service.PlayerService;
import io.github.saimonovski.versechest.common.ServiceManager;
import io.github.saimonovski.versechest.common.injector.ConfigModule;
import io.github.saimonovski.versechest.common.injector.ServiceModule;
import io.github.saimonovski.versechest.listener.ChestCreatedListener;
import io.github.saimonovski.versechest.config.service.ConfigService;
import io.github.saimonovski.versechest.listener.ChestOpenedListener;
import io.github.saimonovski.versechest.listener.ChestRemovedListener;
import io.github.saimonovski.versechest.util.ChatUtil;
import io.github.saimonovski.versechest.util.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.InvUI;

public final class VerseChest extends JavaPlugin {
    private Logger logger;
    Injector injector;
    ServiceManager serviceManager;
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
        loadInjector();
        loadServices();
        registerEvents();
        this.logger.debug("loading Invui");
        InvUI.getInstance();
        this.logger.info("Plugin loaded, if you met any issues report them on our discord: https://discord.gg/4KnFMQmf6z");
    }

    private void loadInjector() {
        Injector serviceInjector = Guice.createInjector(new ServiceModule(this));
        ConfigService configService = serviceInjector.getInstance(ConfigService.class);
        ConfigModule configModule = new ConfigModule(configService);
        this.injector = serviceInjector.createChildInjector(configModule);
        this.logger.debug("loaded injector");
    }

    private void loadServices(){
        this.serviceManager = ServiceManager.getInstance(this.injector,
                ChestService.class,
                ConfigService.class,
                RarityService.class,
                PlayerService.class,
                ChestItemService.class
                );
        this.logger.debug("loaded services");
    }

    private void registerEvents(){
        getServer().getPluginManager().registerEvents(injector().getInstance(ChestCreatedListener.class),this);
        getServer().getPluginManager().registerEvents(injector().getInstance(ChestOpenedListener.class),this);
        getServer().getPluginManager().registerEvents(injector().getInstance(ChestRemovedListener.class),this);
        this.logger.debug("loaded listeners");
    }


    @Override
    public void onDisable() {
        this.serviceManager.disableAll();
    }

    @NotNull
    public Logger logger() {
        return logger;
    }
    public Injector injector(){
        return this.injector;
    }




}
