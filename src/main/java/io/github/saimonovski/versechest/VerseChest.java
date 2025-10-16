package io.github.saimonovski.versechest;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.github.saimonovski.versechest.chest.service.ChestService;
import io.github.saimonovski.versechest.injector.DependencyInjector;
import io.github.saimonovski.versechest.listener.ChestCreatedListener;
import io.github.saimonovski.versechest.config.service.ConfigService;
import io.github.saimonovski.versechest.listener.ChestOpenedListener;
import io.github.saimonovski.versechest.listener.ChestRemovedListener;
import io.github.saimonovski.versechest.util.ChatUtil;
import io.github.saimonovski.versechest.util.Logger;
import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.model.Dependency;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.InvUI;

public final class VerseChest extends JavaPlugin {
    private Logger logger;
    private ConfigService configService;
    Injector injector;

    @Override
    public void onEnable() {
        String text = """
                           \s
                            ____   ____                           _________ .__                     __
                            \\   \\ /   /___________  ______ ____   \\_   ___ \\|  |__   ____   _______/  |_
                             \\   Y   // __ \\_  __ \\/  ___// __ \\  /    \\  \\/|  |  \\_/ __ \\ /  ___/\\   __\\
                              \\     /\\  ___/|  | \\/\\___ \\\\  ___/  \\     \\___|   Y  \\  ___/ \\___ \\  |  |
                               \\___/  \\___  >__|  /____  >\\___  >  \\______  /___|  /\\___  >____  > |__|
                                          \\/           \\/     \\/          \\/     \\/     \\/     \\/
                                         \s
               \s""";
        Bukkit.getConsoleSender().sendMessage(ChatUtil.fix("<bold> <rainbow>" +text+"</rainbow> "));
        this.logger = new Logger(this,false);
        this.injector = Guice.createInjector(new DependencyInjector(this));
        this.logger.debug("loading Invui");
        InvUI.getInstance();

    }
    private void loadServices(){
        this.logger.info("loading config");
        this.configService = new ConfigService(this);
        this.logger.debug("registering listeners");
    }

    private void registerEvents(){
        getServer().getPluginManager().registerEvents(injector().getInstance(ChestCreatedListener.class),this);
        getServer().getPluginManager().registerEvents(injector().getInstance(ChestOpenedListener.class),this);
        getServer().getPluginManager().registerEvents(injector().getInstance(ChestRemovedListener.class),this);
    }


    @Override
    public void onDisable() {

    }

    @NotNull
    public Logger logger() {
        return logger;
    }
    public Injector injector(){
        return this.injector;
    }

    public ConfigService getConfigService() {
        return configService;
    }


}
