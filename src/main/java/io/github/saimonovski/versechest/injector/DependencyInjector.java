package io.github.saimonovski.versechest.injector;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import io.github.saimonovski.versechest.VerseChest;
import io.github.saimonovski.versechest.chest.item.service.ChestItemService;
import io.github.saimonovski.versechest.chest.rarity.service.RarityService;
import io.github.saimonovski.versechest.chest.service.ChestService;
import io.github.saimonovski.versechest.chestPlayer.service.PlayerService;
import io.github.saimonovski.versechest.config.MainConfig;
import io.github.saimonovski.versechest.config.service.ConfigService;
import io.github.saimonovski.versechest.database.Database;
import io.github.saimonovski.versechest.util.Logger;

public class DependencyInjector extends AbstractModule {
    private final VerseChest plugin;
    public DependencyInjector(VerseChest plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {
        bind(VerseChest.class).toInstance(plugin);
        bind(ChestService.class).in(Singleton.class);
        bind(ChestItemService.class).in(Singleton.class);
        bind(RarityService.class).in(Singleton.class);
        bind(ConfigService.class).in(Singleton.class);
        bind(Logger.class).toInstance(this.plugin.logger());
        bind(PlayerService.class).in(Singleton.class);
    }
}
