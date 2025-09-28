package io.github.saimonovski.versechest;
import io.github.saimonovski.versechest.service.ConfigService;
import io.github.saimonovski.versechest.util.Logger;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class VerseChest extends JavaPlugin {
    private Logger logger;
    private ConfigService configService;

    @Override
    public void onEnable() {
        this.logger = new Logger(this,false);

    }
    private void loadConfigService(){
        this.logger.info("Loading config");
        this.configService = new ConfigService(this);
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
