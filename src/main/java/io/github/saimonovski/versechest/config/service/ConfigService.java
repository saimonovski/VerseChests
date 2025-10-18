package io.github.saimonovski.versechest.config.service;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import dev.dejvokep.boostedyaml.spigot.SpigotSerializer;
import io.github.saimonovski.versechest.VerseChest;
import io.github.saimonovski.versechest.common.Service;
import io.github.saimonovski.versechest.config.MainConfig;
import io.github.saimonovski.versechest.config.MessageConfig;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ConfigService implements Service {
    private final VerseChest verseChest;
    private MessageConfig messageConfig;
    private MainConfig mainConfig;
    private YamlDocument mainConfigYamlDocument;
    private YamlDocument messageConfigYamlDocument;

    public ConfigService(VerseChest verseChest) {
        this.verseChest = verseChest;
        initalizeMainConfig();
        initalizeMessageConfig();
    }
    private void initalizeMainConfig(){
        try {
             this.mainConfigYamlDocument = initalizeConfig("config.yml");
        } catch (IOException e) {
            this.verseChest.logger().error("error while loading config.yml ! \n"+e.getCause());
            verseChest.getServer().getPluginManager().disablePlugin(verseChest);
        }
        this.mainConfig = new MainConfig(this.mainConfigYamlDocument,this.verseChest.logger());
    }
    private void initalizeMessageConfig(){
        try {
            this.messageConfigYamlDocument = initalizeConfig("messages.yml");
        } catch (IOException e) {
            this.verseChest.logger().error("error while loading messages.yml ! \n"+e.getCause());
            verseChest.getServer().getPluginManager().disablePlugin(verseChest);
        }
        this.messageConfig = new MessageConfig(this.messageConfigYamlDocument);
    }

    private YamlDocument initalizeConfig(String path) throws IOException {
        return YamlDocument.create(new File(this.verseChest.getDataFolder(),path),
                Objects.requireNonNull(this.verseChest.getResource(path)),
                GeneralSettings.builder().setSerializer(SpigotSerializer.getInstance()).build(),
                LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning("config-version")).build());
    }
    @Override
    public void reload()  {
        try {
            this.mainConfigYamlDocument.reload();
            this.messageConfigYamlDocument.reload();
        } catch (IOException e) {
            this.verseChest.logger().error("error while reloading configs ! \n "+e.getCause());
            verseChest.getServer().getPluginManager().disablePlugin(verseChest);
        }
        this.messageConfig.loadMessages(messageConfigYamlDocument);
        this.mainConfig.loadMainConfig(mainConfigYamlDocument,verseChest.logger());
    }

    public MessageConfig getMessageConfig() {
        return messageConfig;
    }

    public MainConfig getMainConfig() {
        return mainConfig;
    }
}
