package io.github.saimonovski.versechest;
import io.github.saimonovski.versechest.chest.service.ChestService;
import io.github.saimonovski.versechest.listener.ChestCreatedListener;
import io.github.saimonovski.versechest.config.service.ConfigService;
import io.github.saimonovski.versechest.listener.ChestOpenedListener;
import io.github.saimonovski.versechest.util.Logger;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class VerseChest extends JavaPlugin {
    //todo change all services to one service manager and change constructors in listeners to use service manager.
    private Logger logger;
    private ConfigService configService;
    private ChestService chestService;
    @Override
    public void onEnable() {
        this.logger = new Logger(this,false);

    }
    private void loadServices(){
        this.logger.info("loading configs");
        this.configService = new ConfigService(this);
        this.logger.info("Loading services...");
        this.chestService = new ChestService();

    }

    private void loadListeners(){
//todo
    }

    @Override
    public void onDisable() {

    }

    @NotNull
    public Logger logger() {
        return logger;
    }

    public ConfigService getConfigService() {
        return configService;
    }
}
