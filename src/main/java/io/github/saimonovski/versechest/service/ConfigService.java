package io.github.saimonovski.versechest.service;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import dev.dejvokep.boostedyaml.spigot.SpigotSerializer;
import io.github.saimonovski.versechest.VerseChest;
import io.github.saimonovski.versechest.config.MainConfig;
import io.github.saimonovski.versechest.config.MessageConfig;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ConfigService {
    private final VerseChest verseChest;
    private MessageConfig messageConfig;
    private MainConfig mainConfig;
    private YamlDocument mainConfigYamlDocument;

    public ConfigService(VerseChest verseChest) {
        this.verseChest = verseChest;
    }
    private void initalizeMainConfig(){
        try {
             this.mainConfigYamlDocument =  YamlDocument.create(new File(this.verseChest.getDataFolder(),"config.yml"),
                     Objects.requireNonNull(this.verseChest.getResource("config.yml")),
                     GeneralSettings.builder().setSerializer(SpigotSerializer.getInstance()).build(),
                     LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning("config-version")).build());
        } catch (IOException e) {
            this.verseChest.logger().error("error while loading config.yml !"+e.getCause());
            verseChest.getServer().getPluginManager().disablePlugin(verseChest);
        }
        this.mainConfig = new MainConfig(this.mainConfigYamlDocument,this.verseChest.logger());
    }
    public void disableAll(){

    }
    public void reload(){

    }

}
