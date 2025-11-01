package io.github.saimonovski.versechest.paper.module;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import io.github.saimonovski.versechest.paper.VerseChest;
import io.github.saimonovski.versechest.common.application.service.impl.ChestService;
import io.github.saimonovski.versechest.common.application.service.impl.ChestItemService;
import io.github.saimonovski.versechest.common.application.service.impl.PlayerService;
import io.github.saimonovski.versechest.common.application.service.impl.RarityService;
import io.github.saimonovski.versechest.paper.config.service.ConfigService;
import io.github.saimonovski.versechest.paper.util.Logger;

public class ServiceModule extends AbstractModule {
    private final VerseChest plugin;
    public ServiceModule(VerseChest plugin) {
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
